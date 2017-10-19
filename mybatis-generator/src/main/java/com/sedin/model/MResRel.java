package com.sedin.model;

import javax.persistence.*;

@Table(name = "m_res_rel")
public class MResRel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "res_id")
    private Long resId;

    @Column(name = "rel_id")
    private Long relId;

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
     * @return res_id
     */
    public Long getResId() {
        return resId;
    }

    /**
     * @param resId
     */
    public void setResId(Long resId) {
        this.resId = resId;
    }

    /**
     * @return rel_id
     */
    public Long getRelId() {
        return relId;
    }

    /**
     * @param relId
     */
    public void setRelId(Long relId) {
        this.relId = relId;
    }
}