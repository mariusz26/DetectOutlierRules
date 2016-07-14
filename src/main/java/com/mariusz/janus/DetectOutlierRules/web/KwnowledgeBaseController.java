package com.mariusz.janus.DetectOutlierRules.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mariusz.janus.DetectOutlierRules.domain.KnowledgeBase;
import com.mariusz.janus.DetectOutlierRules.domain.ServerProperty;

import lombok.Getter;
import lombok.Setter;


@ManagedBean
@ViewScoped
public class KwnowledgeBaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(KwnowledgeBaseController.class);
	@Getter @Setter private RestTemplate rest;
	@Getter @Setter private List<KnowledgeBase> listBases;
	@Getter @Setter private List<KnowledgeBase> filteredListBases;
	private int index=1;
	
	@ManagedProperty(value = "#{sessionUserController}")
	@Getter @Setter private SessionUserController sessionUser;
	
	public KwnowledgeBaseController() {
		rest = new RestTemplate();
		listBases = new ArrayList<>();
		filteredListBases = new ArrayList<>();
	}

	@PostConstruct
	public void initKnowledgeBase()
	{
		requstForKnowledgeBase();
	}
	
	private void requstForKnowledgeBase()
	{
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization","Bearer "+sessionUser.getToken().getAccess_token());
		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<List<KnowledgeBase>> response = rest.exchange(ServerProperty.SERVER_URL+ServerProperty.KNOWLEDGEBASE, HttpMethod.GET, request,new ParameterizedTypeReference<List<KnowledgeBase>>() {
		});
		
		listBases=response.getBody();
		
		logger.debug("Sprawdzenie listy z bazami");
	}

	public int getIndex() {
		return index++;
	}
	
}
