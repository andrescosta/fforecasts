package com.eb.integration.appdirect.models;

import java.util.Collection;
import java.util.UUID;

import org.springframework.hateoas.Link;

import com.eb.store.models.IdentityProviderMetadata;
import com.eb.store.models.IdentityProviderType;
import com.eb.store.models.Subscription;
import com.eb.store.models.SubscriptionStatus;
import com.eb.store.models.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventData {
	
	private String type;
	private MarketPlace marketplace;
	private AppdirectUser creator;
	private String flag;
	private String returnUrl;
	private String applicationUuid;
	private Collection<Link> links;
	public EventData() {
		super();
	}

	public String getApplicationUuid() {
		return applicationUuid;
	}

	public void setApplicationUuid(String applicationUuid) {
		this.applicationUuid = applicationUuid;
	}

	private Payload payload;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MarketPlace getMarketplace() {
		return marketplace;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public Collection<Link> getLinks() {
		return links;
	}

	public void setLinks(Collection<Link> links) {
		this.links = links;
	}

	public String getFlag() {
		return flag;
	}

	public AppdirectUser getCreator() {
		return creator;
	}

	public void setCreator(AppdirectUser creator) {
		this.creator = creator;
	}

	public String isFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	public void setMarketplace(MarketPlace marketPlace) {
		this.marketplace = marketPlace;
	}
	
	public Subscription AsNewSubscription()
	{
		Subscription subscription = new Subscription();
		subscription.setActive(true);
		subscription.setQuantity(getPayload().getOrder().getItems().get(0).getQuantity());
		subscription.setIdentifier(UUID.randomUUID().toString());
		IdentityProviderMetadata metadata = new IdentityProviderMetadata();
		if (getLinks()!=null && !getLinks().isEmpty())
			metadata.setSamlMetadataURI(getLinks().iterator().next().getHref());
		else
			metadata.setSamlMetadataURI("uri");
		metadata.setType(IdentityProviderType.SAML);
		subscription.setIdentityProviderMetadata(metadata);
		subscription.setStatus(SubscriptionStatus.ACTIVE);
		subscription.addUser(getCreator().asUser());
		return subscription;
	}
	
	@Override
	public String toString() {
		return "EventData [type=" + type + ", marketplace=" + marketplace + ", creator=" + creator + ", flag=" + flag
				+ ", returnUrl=" + returnUrl + ", applicationUuid=" + applicationUuid + ", links=" + links
				+ ", payload=" + payload + "]";
	}

}