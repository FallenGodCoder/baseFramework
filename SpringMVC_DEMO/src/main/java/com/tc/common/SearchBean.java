package com.tc.common;

import java.util.Collection;
import java.util.List;

public class SearchBean {

	private static final long serialVersionUID = -6622632588357979126L;

	private String userId;
	private String userCode;
	private String keyword;
	private List<String> tags;
	private String tagBussType;
	private String searchCriteria01;// String类型的查询条件01
	private String searchCriteria02;// String类型的查询条件02
	private String searchCriteria03;
	private String searchCriteria04;
	private String searchCriteria05;
	private String searchCriteria06;
	private String searchCriteria07;
	private Integer integerCriteria01;
	private Integer integerCriteria02;
	private Object objectCriteria01;
	private Collection<?> collectionCriteria01;
	private Collection<?> collectionCriteria02;
	private Object vo;
	private Object vo2;
	private String paramSql;

	public SearchBean() {

	}

	public String getSearchCriteria01() {
		return searchCriteria01;
	}

	public void setSearchCriteria01(String searchCriteria01) {
		this.searchCriteria01 = searchCriteria01;
	}

	public String getSearchCriteria02() {
		return searchCriteria02;
	}

	public void setSearchCriteria02(String searchCriteria02) {
		this.searchCriteria02 = searchCriteria02;
	}

	public String getSearchCriteria07() {
		return searchCriteria07;
	}

	public void setSearchCriteria07(String searchCriteria07) {
		this.searchCriteria07 = searchCriteria07;
	}

	public Object getVo() {
		return vo;
	}

	public void setVo(Object vo) {
		this.vo = vo;
	}

	@Override
	public String toString() {
		return "SearchBean [searchCriteria01=" + searchCriteria01 + ", searchCriteria02=" + searchCriteria02 + ", vo="
				+ vo + "]";
	}

	public String getSearchCriteria03() {
		return searchCriteria03;
	}

	public void setSearchCriteria03(String searchCriteria03) {
		this.searchCriteria03 = searchCriteria03;
	}

	public String getSearchCriteria04() {
		return searchCriteria04;
	}

	public void setSearchCriteria04(String searchCriteria04) {
		this.searchCriteria04 = searchCriteria04;
	}

	public String getSearchCriteria05() {
		return searchCriteria05;
	}

	public void setSearchCriteria05(String searchCriteria05) {
		this.searchCriteria05 = searchCriteria05;
	}

	public String getSearchCriteria06() {
		return searchCriteria06;
	}

	public void setSearchCriteria06(String searchCriteria06) {
		this.searchCriteria06 = searchCriteria06;
	}

	public Integer getIntegerCriteria01() {
		return integerCriteria01;
	}

	public void setIntegerCriteria01(Integer integerCriteria01) {
		this.integerCriteria01 = integerCriteria01;
	}

	public Integer getIntegerCriteria02() {
		return integerCriteria02;
	}

	public void setIntegerCriteria02(Integer integerCriteria02) {
		this.integerCriteria02 = integerCriteria02;
	}

	public Object getVo2() {
		return vo2;
	}

	public void setVo2(Object vo2) {
		this.vo2 = vo2;
	}

	public Object getObjectCriteria01() {
		return objectCriteria01;
	}

	public void setObjectCriteria01(Object objectCriteria01) {
		this.objectCriteria01 = objectCriteria01;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Collection<?> getCollectionCriteria01() {
		return collectionCriteria01;
	}

	public void setCollectionCriteria01(Collection<?> collectionCriteria01) {
		this.collectionCriteria01 = collectionCriteria01;
	}

	public Collection<?> getCollectionCriteria02() {
		return collectionCriteria02;
	}

	public void setCollectionCriteria02(Collection<?> collectionCriteria02) {
		this.collectionCriteria02 = collectionCriteria02;
	}

	public String getTagBussType() {
		return tagBussType;
	}

	public void setTagBussType(String tagBussType) {
		this.tagBussType = tagBussType;
	}

	public String getParamSql() {
		return paramSql;
	}
	
	public void setParamSql(String paramSql) {
		this.paramSql = paramSql;
	}
	
	public String getUserCode() {
		return userCode;
	}
	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}
