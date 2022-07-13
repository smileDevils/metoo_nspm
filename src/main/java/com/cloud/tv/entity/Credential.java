package com.cloud.tv.entity;

import com.cloud.tv.core.domain.IdEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Access;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Credential extends IdEntity {

    private Integer currentPage = 1;// 当前页数
    private Integer pageSize = 15;// 每页条数
    @ApiModelProperty("凭据Uuid")
    private String uuid;
    @ApiModelProperty("凭据名")
    private String name;
    @ApiModelProperty("用户名")
    private String loginName;
    @ApiModelProperty("密码")
    private String loginPassword;
    @ApiModelProperty("通行用户名")
    private String enableUserName;
    @ApiModelProperty("通行密码")
    private String enablePassword;
    @ApiModelProperty("等级Id")
    private Long branchId;
    @ApiModelProperty("等级名称")
    private String branchName;
    @ApiModelProperty("等级")
    private String branchLevel;

    private String credentialId;
}
