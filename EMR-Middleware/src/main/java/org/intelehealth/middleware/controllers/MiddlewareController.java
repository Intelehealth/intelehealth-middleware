/**
 * 
 */
package org.intelehealth.middleware.controllers;

import java.util.Map;

import javax.sql.DataSource;

import org.intelehealth.middleware.dto.DataTraveller;
import org.intelehealth.middleware.dto.ResponseDTO;
import org.intelehealth.middleware.dto.ReturnPullData;
import org.intelehealth.middleware.dto.ReturnPushData;
import org.intelehealth.middleware.services.AuthService;
import org.intelehealth.middleware.services.ConstantsService;
import org.intelehealth.middleware.services.PullService;
import org.intelehealth.middleware.services.PushService;
import org.intelehealth.middleware.utils.WebClientFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author satya
 *
 */


	@RestController
	@RequestMapping ("/webapi")
	public class MiddlewareController {

		/**
		 * 
		 */
		private static final Logger LOG = LoggerFactory.getLogger(WebClientFilter.class);


		@Autowired
		AuthService authUtil;
		@Autowired
		PullService pullService;
		@Autowired
		ConstantsService constantsService;
		@CrossOrigin(origins = "*")
		@GetMapping("/pulldata/{locationuuid}/{datefrom}")
		@ResponseBody
		public Object pulldata(@PathVariable ("locationuuid") String locationUUID, @PathVariable ("datefrom") String dateFrom , @RequestHeader Map<String, String> headers) {
			if(!authUtil.validate(headers.get("authorization")) || headers.get("authorization") == null) {
				ResponseDTO responsedto = new ResponseDTO();
				responsedto.setStatusMessage(constantsService.ERROR, constantsService.AUTHERROR, constantsService.UNABLETOPROCESS);
				responsedto.setStatus("403");
				return responsedto; 
				
			}
			else {
				ResponseDTO responsedto = new ResponseDTO();
				responsedto.setStatus(constantsService.OK);
			//	responsedto.setStatus("200");
				
				
				Object o = new Object();
				 
				o = pullService.gatherData(locationUUID, dateFrom);
				responsedto.setData(o);
				return responsedto;
				
			}
			//return null;
			
			
		
		}
		
		@Autowired
		PushService pushService;
		@PostMapping("/pushdata")
		@CrossOrigin(origins = "*")
		@ResponseBody
		public Object pushdata(  @RequestHeader Map<String, String> headers,@RequestBody DataTraveller dataTraveller) {
			 
			if(!authUtil.validate(headers.get("authorization")) || headers.get("authorization") == null) {
				ResponseDTO responsedto = new ResponseDTO();
				responsedto.setStatusMessage(constantsService.ERROR, constantsService.AUTHERROR, constantsService.UNABLETOPROCESS);
				responsedto.setStatus("403");
				return responsedto; 
				
			}
			else {
				LOG.debug("Auth Validated");
				return pushService.returnData(dataTraveller, headers.get("authorization") );
				
			}
			// return null;
		 
			
		}
		
		
 		

}
