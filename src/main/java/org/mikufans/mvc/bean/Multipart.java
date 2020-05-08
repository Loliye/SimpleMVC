package org.mikufans.mvc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.mikufans.core.bean.BaseBean;

import java.io.InputStream;

@Data
@AllArgsConstructor
public class Multipart extends BaseBean
{
    private String fieldName;
    private String fileName;
    private long fileSize;
    private String contentType;
    private InputStream inputStream;
}
