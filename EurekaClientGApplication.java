package com.fedex;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class EurekaClientGApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(EurekaClientGApplication.class, args);
	}
	

		@Autowired
		private DiscoveryClient discoveryClient;
		
		
		@RequestMapping("/hello")
		  public String hello() {
			List<ServiceInstance> instances=discoveryClient.getInstances("eurekaserviceg");
			System.out.println(instances);
			ServiceInstance serviceInstance=instances.get(0);
			System.out.println(serviceInstance.getServiceId());
			String baseUrl=serviceInstance.getUri().toString();
			baseUrl=baseUrl+"/hello";
			System.out.println(baseUrl);
			RestTemplate restTemplate = new RestTemplate();
		    URI uri = URI.create(baseUrl);

		    return restTemplate.getForObject(uri, String.class);
		   
		  }
		
		 @RequestMapping("/service-instances/{applicationName}")
		    public List<ServiceInstance> serviceInstancesByApplicationName(
		            @PathVariable String applicationName) {
		        return this.discoveryClient.getInstances(applicationName);
		    }
		
		}


