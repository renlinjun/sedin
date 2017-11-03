package com.sedin.model;

import javax.persistence.*;

@Table(name = "m_res")
public class MRes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 002=角色 003=menu  001=人员
     */
    private String type;

    private Integer sort;

    /**
     * 0-正常 -1-删除
     */
    private Integer status;

    private String url;

    @Column(name = "res_pic")
    private String resPic;

    private String description;

    @Column(name = "file_path")
    private String filePath;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return parent_id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取002=角色 003=menu  001=人员
     *
     * @return type - 002=角色 003=menu  001=人员
     */
    public String getType() {
        return type;
    }

    /**
     * 设置002=角色 003=menu  001=人员
     *
     * @param type 002=角色 003=menu  001=人员
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取0-正常 -1-删除
     *
     * @return status - 0-正常 -1-删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0-正常 -1-删除
     *
     * @param status 0-正常 -1-删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return res_pic
     */
    public String getResPic() {
        return resPic;
    }
    /**
     * @return file_path
     */
    public String getFilePath() {
        return filePath;
    }
    /**
     * @param filePath

     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @param resPic

     */
    public void setResPic(String resPic) {
        this.resPic = resPic;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}