package com.sedin.uc.service;

/**
 * Created by liuhan on 2017-11-04.
 */
public interface RelResService {

    /**
     * 有资源引用这个
     * @param id
     * @return
     */
    public boolean hasResRefThis(Long id);

    /**
     * 删除和这个资源有关系的引用Res
     * @param id
     */
    public void delResRelByRef(Long id );

    /**
     * 删除和这个资源有关系的引用Rel
     * @param id
     */
    public void delResRelByRefRel(Long id );
}
