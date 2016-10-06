package com.oigoiania.parsers;

import java.io.Serializable;

public class MyBaseDataHolder  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String loc_id; // id
	private String loc_title; // titulo
	private String loc_description; // descricao
	private String loc_address; // endereco
	private double loc_longitude;
	private double loc_latitude;
	private String loc_time; // horario
	private String loc_image; // imagem
	private String loc_catId; // categoria
	private String loc_subCateId; // subcategoria
	private String loc_promotionId; // promocoes
	private String loc_link;
	private String loc_telephone;
	private String loc_cell;
	private String loc_site;

	/**
	 * @return the loc_id
	 */
	public String getLoc_id() {
		return loc_id;
	}

	/**
	 * @return the loc_title
	 */
	public String getLoc_title() {
		return loc_title;
	}

	/**
	 * @return the loc_description
	 */
	public String getLoc_description() {
		return loc_description;
	}

	/**
	 * @return the loc_address
	 */
	public String getLoc_address() {
		return loc_address;
	}

	/**
	 * @return the loc_longitude
	 */
	public double getLoc_longitude() {
		return loc_longitude;
	}

	/**
	 * @return the loc_latitude
	 */
	public double getLoc_latitude() {
		return loc_latitude;
	}

	/**
	 * @return the loc_time
	 */
	public String getLoc_time() {
		return loc_time;
	}

	/**
	 * @return the loc_image
	 */
	public String getLoc_image() {
		return loc_image;
	}

	/**
	 * @return the loc_catId
	 */
	public String getLoc_catId() {
		return loc_catId;
	}

	/**
	 * @return the loc_subCateId
	 */
	public String getLoc_subCateId() {
		return loc_subCateId;
	}

	/**
	 * @return the loc_promotionId
	 */
	public String getLoc_promotionId() {
		return loc_promotionId;
	}

	/**
	 * @return the loc_link
	 */
	public String getLoc_link() {
		return loc_link;
	}

	/**
	 * @return the loc_telephone
	 */
	public String getLoc_telephone() {
		return loc_telephone;
	}

	/**
	 * @return the loc_cell
	 */
	public String getLoc_cell() {
		return loc_cell;
	}

	/**
	 * @return the loc_site
	 */
	public String getLoc_site() {
		return loc_site;
	}

	/**
	 * @param loc_id
	 *            the loc_id to set
	 */
	public void setLoc_id(String loc_id) {
		this.loc_id = loc_id;
	}

	/**
	 * @param loc_title
	 *            the loc_title to set
	 */
	public void setLoc_title(String loc_title) {
		this.loc_title = loc_title;
	}

	/**
	 * @param loc_description
	 *            the loc_description to set
	 */
	public void setLoc_description(String loc_description) {
		this.loc_description = loc_description;
	}

	/**
	 * @param loc_address
	 *            the loc_address to set
	 */
	public void setLoc_address(String loc_address) {
		this.loc_address = loc_address;
	}

	/**
	 * @param loc_longitude
	 *            the loc_longitude to set
	 */
	public void setLoc_longitude(double loc_longitude) {
		this.loc_longitude = loc_longitude;
	}

	/**
	 * @param loc_latitude
	 *            the loc_latitude to set
	 */
	public void setLoc_latitude(double loc_latitude) {
		this.loc_latitude = loc_latitude;
	}

	/**
	 * @param loc_time
	 *            the loc_time to set
	 */
	public void setLoc_time(String loc_time) {
		this.loc_time = loc_time;
	}

	/**
	 * @param loc_image
	 *            the loc_image to set
	 */
	public void setLoc_image(String loc_image) {
		this.loc_image = loc_image;
	}

	/**
	 * @param loc_catId
	 *            the loc_catId to set
	 */
	public void setLoc_catId(String loc_catId) {
		this.loc_catId = loc_catId;
	}

	/**
	 * @param loc_subCateId
	 *            the loc_subCateId to set
	 */
	public void setLoc_subCateId(String loc_subCateId) {
		this.loc_subCateId = loc_subCateId;
	}

	/**
	 * @param loc_promotionId
	 *            the loc_promotionId to set
	 */
	public void setLoc_promotionId(String loc_promotionId) {
		this.loc_promotionId = loc_promotionId;
	}

	/**
	 * @param loc_link
	 *            the loc_link to set
	 */
	public void setLoc_link(String loc_link) {
		this.loc_link = loc_link;
	}

	/**
	 * @param loc_telephone
	 *            the loc_telephone to set
	 */
	public void setLoc_telephone(String loc_telephone) {
		this.loc_telephone = loc_telephone;
	}

	/**
	 * @param loc_cell
	 *            the loc_cell to set
	 */
	public void setLoc_cell(String loc_cell) {
		this.loc_cell = loc_cell;
	}

	/**
	 * @param loc_site
	 *            the loc_site to set
	 */
	public void setLoc_site(String loc_site) {
		this.loc_site = loc_site;
	}

}
