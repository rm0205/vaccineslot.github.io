package com.sample.cowin.app.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;



@RestController
public class HomeController {
	
	@Autowired
	private ServiceLayer serviceLayer;
	
	@GetMapping("/index")
    public String viewHomePage() {
        return "index";
    }
	
		

	
	
	
	@GetMapping(value="/states")
	public ModelAndView getData()
	{

	States statesList=serviceLayer.consumeApi();
		ModelAndView model=new ModelAndView("indiastate");
         model.addObject("statesList" , statesList.getStates());
        // getData1(state);
        model.addObject("stateName", new State());
         return model;		 
	}

	@GetMapping(value="/stating_data")
	public ModelAndView SetData(@ModelAttribute(value="stateName")State state)
	{
	     
		
		ModelAndView model=new ModelAndView("indiastate");
		
		//System.out.println(state.getState_id());
		
		
		return getData1(state.getState_id());
		
	}
	
	@RequestMapping(value="/district/{state_id}")
	public  ModelAndView getData1(@PathVariable("state_id") int state_id)
	{
         	
		  ModelAndView model=new ModelAndView("indiastate");
		  model.addObject("districtName", serviceLayer.consumeApi1(state_id).getDistricts());
		  model.addObject("districName", new District());
		  String date = null;
		  model.addObject("date", date);
          return model;
            
//		System.out.println(serviceLayer.consumeApi1(state_id));
//		
	}
	@GetMapping(value="/district_data")
	public ModelAndView SetData1(@ModelAttribute(value="districName")District disti, @ModelAttribute(value="date") String date)
	{
	     
		System.out.println(date);
		//ModelAndView model=new ModelAndView("indiastate");
		 String[] datenew=date.split("-");
	
		 date="";
		 for(int i=datenew.length-1;i>=0;i--)
		 {
			  
			   date=date+datenew[i];
			   if(i>0)
				   date=date+"-";
		 }
		 System.out.println(date);
		  
		System.out.println(disti.getDistrict_id());
		
		
		ModelAndView model=new ModelAndView("states_india");
		
		return GetDisData(disti.getDistrict_id(),date);
		
		
		
		
	}
	
	@GetMapping(value="/find_dis/{district_id}/{date}")
	public ModelAndView GetDisData(@PathVariable("district_id") int district_id, @PathVariable("date") String date)
	{
		
		ModelAndView model=new ModelAndView("states_india");
		model.addObject("sessionName", new Session());
		model.addObject("slotList", serviceLayer.consumeApi2(district_id,date).getSessions());
		return model;
	  //return serviceLayer.consumeApi2(district_id,date).getSessions();	
	}
	
}
 