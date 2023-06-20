package com.tejas.Controller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.tejas.model.ReadProperty;
import com.tejas.dao.AlienRepo;
import com.tejas.dao.AlienService;
import com.tejas.dao.Constants;
import com.tejas.dao.DataRepo;
import com.tejas.dao.MarksRepo;
import com.tejas.model.Alien;
import com.tejas.model.AlienMarksBean;
import com.tejas.model.Data;
import com.tejas.model.Marks;
import com.tejas.model.Root;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AlienController {
	
	static SSLContext sc;

    static {
        try {
            System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
            TrustManager[] trustAllCerts = {(TrustManager) new X509TrustManager() {
            	public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
	
	@Autowired
	AlienRepo repo;
	
	@Autowired
	MarksRepo mark;
	
	@Autowired
	DataRepo data;
	
	@Autowired
	AlienService alienService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ReadProperty readConfig;
	
	@RequestMapping("/")
	public String home() {
		
		return "home";
	}
	
	@GetMapping("/addAlien")
	public String addAlien(Alien alien) {
		repo.save(alien);
		return "home";
	}
	
	@GetMapping("/aliens")
	public List<Alien> getAliens() {
		
		return repo.findAll();
	}
	
	@GetMapping("/alien/{aid}")
	public Alien getAlien(@PathVariable("aid") int aid) {
		
		return repo.findById(aid).orElse(new Alien());
		
	}
	
	@DeleteMapping("/alien/{aid}")
	public Alien deleteAlien(@PathVariable int aid) {
		
		try {
			
			return repo.findById(aid).orElse(new Alien());
		}
		
		finally {
			System.out.println("hello");
			repo.deleteById(aid);
		}
	}
	
	@PutMapping("/alien")
	public Alien updateAlien(@RequestBody Alien a) {
		
		repo.deleteById(a.getAid());
		repo.save(a);
		
		return a;
	}
	
	@PutMapping("/sendAlien")
	public Alien sendAlien(@RequestBody Alien a) {
		
		System.out.println(a.toString());
		addAlien(a);
		return a;
	}
	
	@GetMapping("/alienMarks")
	public List<AlienMarksBean> getAlienMarks() {
		
		List<Alien> aList = repo.findAlienByAid();
		System.out.println(aList);
		List<Marks> mList = mark.findMarksByAid();
		System.out.println(mList);
		return alienService.getAllDetails(aList, mList);
	}
	
   @GetMapping("/products")
   public Root getProductList(HttpServletResponse response) {
      HttpHeaders headers = new HttpHeaders();
      headers.add("Host", readConfig.getHost());
      headers.add("Accept", readConfig.getAccept());
      headers.add("Connection", readConfig.getConnection());
      
      HttpEntity<Root> entity = new HttpEntity<Root>(headers);
      
      Root bean = new Root();
      
      try {
    	  bean = restTemplate.exchange(readConfig.getUrl(), HttpMethod.GET, entity, Root.class).getBody();
          
          Iterable<Data> iterable = bean.getData();
          data.saveAll(iterable);
          bean.setCode(Constants.OK_CODE);
    	  bean.setDescription(Constants.OK_DESCRIPTION);
          
      } catch(HttpClientErrorException e){
    	  e.printStackTrace();
    	  response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    	  bean.setCode(Constants.NOT_FOUND_CODE);
    	  bean.setDescription(Constants.NOT_FOUND_DESCRIPTION);
    	  
      } catch(HttpServerErrorException e){
    	  e.printStackTrace();
    	  response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    	  bean.setCode(Constants.ERROR_CODE);
    	  bean.setDescription(Constants.ERROR_DESCRIPTION);
    	  
      } catch(Exception e){
    	  e.printStackTrace();
    	  response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    	  bean.setCode(Constants.UNKNOWN_ERROR_CODE);
    	  bean.setDescription(Constants.UNKNOWN_ERROR_DESCRIPTION);
      } 
      
      return bean;
   }

}
