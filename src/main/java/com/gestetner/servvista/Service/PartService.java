package com.gestetner.servvista.Service;
import com.gestetner.servvista.Dto.parts.*;
import com.gestetner.servvista.Models.entity.parts.*;
import com.gestetner.servvista.Repositories.identity.UserRepository;
import com.gestetner.servvista.Repositories.machines.MachineModelRepository;
import com.gestetner.servvista.Repositories.parts.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service @Transactional
public class PartService {
    private final PartRepository parts; private final PartMachineModelRepository mappings;
    private final PartCategoryMappingRepository categoryMappings; private final PartCategoryRepository categories;
    private final MachineModelRepository models; private final UserRepository users;
    public PartService(PartRepository p, PartMachineModelRepository m, PartCategoryMappingRepository pcm,
                       PartCategoryRepository pc, MachineModelRepository mm, UserRepository u){parts=p;mappings=m;categoryMappings=pcm;categories=pc;models=mm;users=u;}
    public PartResponse create(PartRequest r){validate(r,null); Part p=new Part(); apply(p,r,true); p=parts.saveAndFlush(p); replace(p.getPartId(),r.modelIds());replaceCategories(p.getPartId(),r.categoryIds()); return response(p);}
    @Transactional(readOnly=true) public List<PartResponse> getAll(){return parts.findAll(Sort.by("partId")).stream().map(this::response).toList();}
    @Transactional(readOnly=true) public PartResponse getById(Long id){return response(find(id));}
    public PartResponse update(Long id,PartRequest r){Part p=find(id);validate(r,id);apply(p,r,false);parts.saveAndFlush(p);replace(id,r.modelIds());replaceCategories(id,r.categoryIds());return response(p);}
    public void delete(Long id){Part p=find(id);try{categoryMappings.deleteAllByPartId(id);categoryMappings.flush();mappings.deleteAllByPartId(id);mappings.flush();parts.delete(p);parts.flush();}catch(DataIntegrityViolationException e){throw new IllegalStateException("Part "+id+" cannot be deleted because it is referenced by estimate lines",e);}}
    private void validate(PartRequest r,Long id){String c=code(r.partCode());boolean exists=id==null?parts.existsByPartCodeIgnoreCase(c):parts.existsByPartCodeIgnoreCaseAndPartIdNot(c,id);if(exists)throw new IllegalStateException("Part code "+c+" already exists");if(r.createdBy()!=null&&!users.existsById(r.createdBy()))throw new EntityNotFoundException("User "+r.createdBy()+" was not found");for(Long mid:new LinkedHashSet<>(r.modelIds()))if(!models.existsById(mid))throw new EntityNotFoundException("Machine model "+mid+" was not found");for(Long cid:new LinkedHashSet<>(safe(r.categoryIds())))if(!categories.existsById(cid))throw new EntityNotFoundException("Part category "+cid+" was not found");}
    private void apply(Part p,PartRequest r,boolean create){p.setPartCode(code(r.partCode()));p.setPartName(r.partName().trim());p.setDescription(opt(r.description()));p.setUnitPrice(r.unitPrice());if(create){p.setIsActive(r.isActive()==null||r.isActive());p.setCreatedBy(r.createdBy());p.setCreatedAt(LocalDateTime.now());}else if(r.isActive()!=null)p.setIsActive(r.isActive());}
    private void replace(Long id,List<Long> ids){mappings.deleteAllByPartId(id);mappings.flush();for(Long mid:new LinkedHashSet<>(ids)){PartMachineModel x=new PartMachineModel();x.setPartId(id);x.setModelId(mid);mappings.save(x);}mappings.flush();}
    private void replaceCategories(Long id,List<Long> ids){categoryMappings.deleteAllByPartId(id);categoryMappings.flush();for(Long cid:new LinkedHashSet<>(safe(ids))){PartCategoryMapping x=new PartCategoryMapping();x.setPartId(id);x.setPartCategoryId(cid);categoryMappings.save(x);}categoryMappings.flush();}
    private Part find(Long id){return parts.findById(id).orElseThrow(()->new EntityNotFoundException("Part "+id+" was not found"));}
    private PartResponse response(Part p){return new PartResponse(p.getPartId(),p.getPartCode(),p.getPartName(),p.getDescription(),p.getUnitPrice(),p.isActive(),p.getCreatedBy(),p.getCreatedAt(),mappings.findAllByPartIdOrderByModelId(p.getPartId()).stream().map(PartMachineModel::getModelId).toList(),categoryMappings.findAllByPartIdOrderByPartCategoryId(p.getPartId()).stream().map(PartCategoryMapping::getPartCategoryId).toList());}
    private List<Long> safe(List<Long> ids){return ids==null?List.of():ids;}
    private String code(String s){return s.trim().toUpperCase(Locale.ROOT);} private String opt(String s){return s==null||s.isBlank()?null:s.trim();}
}
