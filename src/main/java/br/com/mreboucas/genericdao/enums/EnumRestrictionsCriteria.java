package br.com.mreboucas.genericdao.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Marcelo Rebou�as 17 de ago de 2017 - 10:27:21 [marceloreboucas10@gmail.com]
 * @refence: https://docs.jboss.org/hibernate/orm/3.2/api/org/hibernate/criterion/Restrictions.html
 */
public enum EnumRestrictionsCriteria {

	UNKOWN("_oIo"),
	GREATER_THAN("_gt"),
	GREATER_THAN_OR_EQUAL("_ge"),
	LESS_THAN("_lt"),
	LESS_THAN_OR_EQUAL("_le"),
	EQUAL("_eq"),
	NOT_EQUAL("_ne"),
	ILIKE("_ilike"),
	LIKE("_like"),
	IS_NULL("_isNull"),
	IS_NOT_NULL("_isNotNull"),
	IS_EMPTY("_isEmpty"),
	IS_NOT_EMPTY("_isNotEmpty"),
	;
	
	private String restriction;

	public static EnumRestrictionsCriteria getEnumByProperty(String restiction) {
		
		if (StringUtils.isNotBlank(restiction)) {
			
			String param = "";

			if (!restiction.contains("_")) {
				param = "_";
			}
			
			for (EnumRestrictionsCriteria enumRestrictionsCriteria : EnumRestrictionsCriteria.values()) {
				
				if (enumRestrictionsCriteria.getRestriction().equalsIgnoreCase(param + restiction)) {
					return enumRestrictionsCriteria;
				}
				
			}
			
			return EnumRestrictionsCriteria.valueOf(restiction);
		}
		
		return UNKOWN;
		
	}

	public String getRestriction() {
		return restriction;
	}

	private EnumRestrictionsCriteria(String property) {
		this.restriction = property;
	}
}