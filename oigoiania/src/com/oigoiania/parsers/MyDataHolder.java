package com.oigoiania.parsers;

public class MyDataHolder extends MyBaseDataHolder {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double distance;
	private Category category = null/* Categorias */;
	private SubCategory subCategory = null/* SubCategorias */;
	private String id;
	private String name;
	private String description;
	private String dateInitail;
	private String dateFinal;
	private String time;
	private String image;
	private String address;
	private String date;
	private String tabela_precos;
	private String comment;
	private String valid;
	private String type;

	

	public MyDataHolder() {
		category = new Category();
		subCategory = new SubCategory();
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @return the subCategory
	 */
	public SubCategory getSubCategory() {
		return subCategory;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the dateInitail
	 */
	public String getDateInitail() {
		return dateInitail;
	}

	/**
	 * @return the dateFinal
	 */
	public String getDateFinal() {
		return dateFinal;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @param subCategory
	 *            the subCategory to set
	 */
	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param dateInitail
	 *            the dateInitail to set
	 */
	public void setDateInitail(String dateInitail) {
		this.dateInitail = dateInitail;
	}

	/**
	 * @param dateFinal
	 *            the dateFinal to set
	 */
	public void setDateFinal(String dateFinal) {
		this.dateFinal = dateFinal;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	public String getTabela_precos() {
		return tabela_precos;
	}

	public void setTabela_precos(String tabela_precos) {
		this.tabela_precos = tabela_precos;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
