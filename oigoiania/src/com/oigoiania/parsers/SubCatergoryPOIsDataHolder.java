package com.oigoiania.parsers;

public class SubCatergoryPOIsDataHolder extends BaseDataHolder{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String address;
	private String schedule;
	private String siteUrl;
	private String categoryname;
	private String catId;
	private String subCatId;

	public SubCatergoryPOIsDataHolder() {

	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the schedule
	 */
	public String getSchedule() {
		return schedule;
	}

	/**
	 * @param schedule
	 *            the schedule to set
	 */
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	/**
	 * @return the siteUrl
	 */
	public String getSiteUrl() {
		return siteUrl;
	}

	/**
	 * @param siteUrl
	 *            the siteUrl to set
	 */
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	/**
	 * @return the subcategory
	 */
	public String getCategoryName() {
		return categoryname;
	}

	/**
	 * @param subcategory
	 *            the subcategory to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryname = categoryName;
	}

	/**
	 * @return the catId
	 */
	public String getCatId() {
		return catId;
	}

	/**
	 * @return the subCatId
	 */
	public String getSubCatId() {
		return subCatId;
	}

	/**
	 * @param catId the catId to set
	 */
	public void setCatId(String catId) {
		this.catId = catId;
	}

	/**
	 * @param subCatId the subCatId to set
	 */
	public void setSubCatId(String subCatId) {
		this.subCatId = subCatId;
	}

}
