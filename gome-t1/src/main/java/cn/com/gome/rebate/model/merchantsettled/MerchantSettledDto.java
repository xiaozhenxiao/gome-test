package cn.com.gome.rebate.model.merchantsettled;

import java.util.Date;

import cn.com.gome.rebate.model.CommonDto;

public class MerchantSettledDto extends CommonDto{
	
	//商家ID
	private String merchantId;
	//推荐码
	private String recommendCode;
	//商家名称
	private String merchantName;
	//入住时间
	private Date date;
	//账户昵称
	private String accountNick;
	//账户ID
	private String accountId;
	
	public MerchantSettledDto(){}
	
	public MerchantSettledDto(String merchantId, String recommendCode,
			String merchantName, Date date, String accountNick, String accountId) {
		this.merchantId = merchantId;
		this.recommendCode = recommendCode;
		this.merchantName = merchantName;
		this.date = date;
		this.accountNick = accountNick;
		this.accountId = accountId;
	}
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getRecommendCode() {
		return recommendCode;
	}
	public void setRecommendCode(String recommendCode) {
		this.recommendCode = recommendCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAccountNick() {
		return accountNick;
	}
	public void setAccountNick(String accountNick) {
		this.accountNick = accountNick;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
}
