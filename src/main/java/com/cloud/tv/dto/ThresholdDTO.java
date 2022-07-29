package com.cloud.tv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;


@Data
@Accessors
@AllArgsConstructor
@NoArgsConstructor
public class ThresholdDTO extends PageDto<ThresholdDTO> {
    @Min(message = "超过最小值", value = 0)
    private Integer cpu;
    private Integer memory;
    private Integer flow;
}
