package com.eupraxia.telephony.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eupraxia.telephony.DTO.DispositionDTO;
import com.eupraxia.telephony.DTO.SubDispositionDTO;
import com.eupraxia.telephony.Model.DispositionModel;
//import com.eupraxia.telephony.Model.SubDispositionModel;
import com.eupraxia.telephony.service.DispositionService;
import com.eupraxia.telephony.util.CommonUtil;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class DispositionController {
	
	@Autowired 
	private DispositionService dispositionService;
	 @RequestMapping(value="/addDisposition",method = RequestMethod.POST)
	    public ResponseEntity<?> addDisposition(@RequestBody DispositionDTO dispositionDTO)throws InstantiationException, IllegalAccessException{
	    		
	    		Map<Object, Object> model = new HashMap<>(); 
	    		DispositionModel dispositionModel = new DispositionModel();	
	    		//dispositionModel.setId(dispositionDTO.getId());
	    		dispositionModel.setParentId(dispositionDTO.getParentId());
	    		dispositionModel.setDisposition(dispositionDTO.getDisposition());
	    		dispositionService.saveDisposition(dispositionModel);
				
				model.put("responseCode", "200");
	    		model.put("responseMessage", "Success");
	    		model.put("responseType", "Success");  
	    		model.put("disposition", dispositionDTO);
	    		return new ResponseEntity(model, HttpStatus.OK);    
	    
	    }
	 
	   @RequestMapping(value="/showAllDisposition",method = RequestMethod.GET)
	    public ResponseEntity<?> showDispositions() throws InstantiationException, IllegalAccessException{
	    	
	    	List<DispositionDTO> dispositionList = new ArrayList<>();
	    	DispositionDTO dispositionDTO = new DispositionDTO();
	    	Map<Object, Object> model = new HashMap<>();
	    	List<DispositionModel> disposition = dispositionService.findAllDispositions();
	    	if(CommonUtil.isListNotNullAndEmpty(disposition)) {          
	    		dispositionList = CommonUtil.copyListBeanProperty(disposition, DispositionDTO.class);
	    	}
	    	
	    		model.put("responseCode", "200");
	    		model.put("responseMessage", "Success");
	    		model.put("responseType", "Success");  
	    		model.put("disposition", dispositionList);
	    		return new ResponseEntity(model, HttpStatus.OK);       
	    }
	   
	   @RequestMapping(value="/getDispositionsByParentId",method = RequestMethod.GET)
	    public ResponseEntity<?> getAllTopOrderDispositions(@RequestParam("parentId") int parentId) throws InstantiationException, IllegalAccessException{
	    	
	    	List<DispositionDTO> dispositionList = new ArrayList<>();
	    	DispositionDTO dispositionDTO = new DispositionDTO();
	    	Map<Object, Object> model = new HashMap<>();
	    	List<DispositionModel> disposition = dispositionService.findAllDispositionsOFParent(parentId);
	    	if(CommonUtil.isListNotNullAndEmpty(disposition)) {          
	    		dispositionList = CommonUtil.copyListBeanProperty(disposition, DispositionDTO.class);
	    	}
	    	
	    		model.put("responseCode", "200");
	    		model.put("responseMessage", "Success");
	    		model.put("responseType", "Success");  
	    		model.put("disposition", dispositionList);
	    		return new ResponseEntity(model, HttpStatus.OK);       
	    }
	    		
}
	   /* @RequestMapping(value="/addSubDisposition",method = RequestMethod.POST)
		    public ResponseEntity<?> addsubDisposition(@RequestBody SubDispositionDTO subdispositionDTO)throws InstantiationException, IllegalAccessException{
		    		
		    		Map<Object, Object> model = new HashMap<>(); 
		    		SubDispositionModel subdispositionModel = new SubDispositionModel();	
		    		//dispositionModel.setId(dispositionDTO.getId());
		    		subdispositionModel.setDispositionId(subdispositionDTO.getDispositionId());
		    		subdispositionModel.setSubDispositionName(subdispositionDTO.getSubDispositionName());
		    		subdispositionModel.setSubDispositionType(subdispositionDTO.getSubDispositionType());
		    		dispositionService.saveSubDisposition(subdispositionModel);
					
					model.put("responseCode", "200");
		    		model.put("responseMessage", "Success");
		    		model.put("responseType", "Success");  
		    		model.put("subDisposition", subdispositionDTO);
		    		return new ResponseEntity(model, HttpStatus.OK);    
		    
		    }
		 
	
	 * @RequestMapping(value="/getSubDisposition",method = RequestMethod.GET) public
	 * ResponseEntity<?> showSubDispositions() throws InstantiationException,
	 * IllegalAccessException{
	 * 
	 * List<DispositionDTO> subdispositionList = new ArrayList<>();
	 * SubDispositionDTO subdispositionDTO = new SubDispositionDTO(); Map<Object,
	 * Object> model = new HashMap<>(); List<SubDispositionModel> subdisposition =
	 * dispositionService.findAllSubDispositions();
	 * if(CommonUtil.isListNotNullAndEmpty(subdisposition)) { subdispositionList =
	 * CommonUtil.copyListBeanProperty(subdisposition, SubDispositionDTO.class); }
	 * 
	 * model.put("responseCode", "200"); model.put("responseMessage", "Success");
	 * model.put("responseType", "Success"); model.put("disposition",
	 * subdispositionList); return new ResponseEntity(model, HttpStatus.OK); } }
	 */
