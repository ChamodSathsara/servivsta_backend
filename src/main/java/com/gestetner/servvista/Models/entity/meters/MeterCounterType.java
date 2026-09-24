package com.gestetner.servvista.Models.entity.meters;

import com.gestetner.servvista.Models.Enums.Meters.MeterCounterCode;
import jakarta.persistence.*;

/**
 * Generated from the NewServvista DBML schema (converted from C# / EF Core model).
 */
@Entity
@Table(name = "meter_counter_type")
public class MeterCounterType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meter_counter_type_id", nullable = false, updatable = false)
    private Long meterCounterTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "counter_code", nullable = false, length = 32)
    private MeterCounterCode counterCode;

    @Column(name = "counter_name", nullable = false)
    private String counterName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public MeterCounterType() {
    }

    public Long getMeterCounterTypeId() {
        return meterCounterTypeId;
    }

    public void setMeterCounterTypeId(Long meterCounterTypeId) {
        this.meterCounterTypeId = meterCounterTypeId;
    }

    public MeterCounterCode getCounterCode() {
        return counterCode;
    }

    public void setCounterCode(MeterCounterCode counterCode) {
        this.counterCode = counterCode;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
