package org.mikufans.mvc.bean;

import org.mikufans.core.bean.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class Multiparts extends BaseBean
{
    private List<Multipart> multipartList = new ArrayList<>();

    public Multiparts(List<Multipart> multipartList)
    {
        this.multipartList = multipartList;
    }

    public int size()
    {
        return multipartList.size();
    }

    public List<Multipart> getAll()
    {
        return multipartList;
    }

    public Multipart getOne()
    {
        return size() == 1 ? multipartList.get(0) : null;
    }
}
