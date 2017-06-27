package com.skyfervor.framework.orm;

import java.util.Date;

public interface IDataEntity {
	public Byte getEnumDataEntityStatus();
	public void setEnumDataEntityStatus(Byte enumDataEntityStatus);
	public String getCreateOperator();
	public Long getCreateOperatorId();
	public Date getCreateTime();
	public String getLastUpdateOperator();
	public Long getLastUpdateOperatorId();
	public Date getLastUpdateTime();
}
