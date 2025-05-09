package com.iflove.aichat.common.domain.vo.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Schema(description = "基础翻页请求")
public class PageBaseReq {

    @Schema(description = "页面大小")
    @Min(0)
    @Max(50)
    private Integer pageSize = 10;

    @Schema(description = "页面索引（从1开始）")
    private Integer pageNo = 1;

    /**
     * 获取mybatisPlus的page
     *
     * @return
     */
    public Page plusPage() {
        return new Page(pageNo, pageSize);
    }
}
