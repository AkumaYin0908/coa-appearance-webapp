package com.coa.controller;



import com.coa.exceptions.LeaderNotFoundException;

import com.coa.model.Leader;
import com.coa.service.LeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/settings")
public class LeaderController {


    private final LeaderService leaderService;


    private Leader editFormLeader=new Leader();
    @Value("${pageNums}")
    private List<Integer> pageNums;

    @InitBinder
    public void getInitBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor=new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }

    @GetMapping("/leaders")
    public String showLeaders(Model model, @RequestParam(required = false) String searchName,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id,asc") String[] sort){

        try{
            String sortField = sort[0];
            String sortDirection = sort[1];

            Sort.Direction direction=sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

            Sort.Order order=new Sort.Order(direction,sortField);

            Pageable pageable = PageRequest.of(page-1,size,Sort.by(order));

            Page<Leader> leaderPage;
            if (searchName == null || searchName.isEmpty()) {
                leaderPage = leaderService.findAll(pageable);
            }
            else {
                leaderPage=leaderService.findByNameContainingIgnoreCase(searchName,pageable);
                model.addAttribute("searchName",searchName);
            }

            List<String> leaderPositions= leaderService.findAll().stream()
                    .map(Leader :: getPosition).toList();




            List<Leader> leaders = leaderPage.getContent();

            model.addAttribute("addFormLeader",new Leader());
            model.addAttribute("editFormLeader",editFormLeader);
            model.addAttribute("leaderPositions",leaderPositions);
            model.addAttribute("leaders",leaders);
            model.addAttribute("currentPage", leaderPage.getNumber() + 1);
            model.addAttribute("totalPages",leaderPage.getTotalPages());
            model.addAttribute("pageSize",size);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
            model.addAttribute("pageNums", pageNums);
        }catch (Exception ex){
            model.addAttribute("message", ex.getMessage());
        }

        return "settings/leaders";
    }

    @PostMapping("/leaders/save")
    public String saveLeader(@ModelAttribute("addFormLeader") Leader leader, RedirectAttributes redirectAttributes){
        try{

            String name=leader.getName();
            Optional<Leader> leaderOptional=leaderService.findLeaderByName(name);

            if(leaderOptional.isPresent()){
                redirectAttributes.addFlashAttribute("addModalMessage", "Leader's name already exists!");
                return  "redirect:/settings/leaders";

            }else{
                boolean inCharge=leader.isInCharge();

                if(inCharge){
                    Long numOfIncharge=leaderService.countByInCharge();

                    if(numOfIncharge == 1){
                        redirectAttributes.addFlashAttribute("addModalMessage", "There must be only one leader in charge!");
                        return "redirect:/settings/leaders";
                    }
                }

                leaderService.save(leader);
                redirectAttributes.addFlashAttribute("message", leader.getName() + " has been added!");
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            ex.printStackTrace();
        }

        return "redirect:/settings/leaders";
    }


    @PostMapping("/leaders/update")
    public String updateLeader(@ModelAttribute("editFormLeader") Leader leader,RedirectAttributes redirectAttributes){

        try{

            Optional<Leader> optionalLeader=leaderService.findLeaderByName(leader.getId(),leader.getName());

            if(optionalLeader.isPresent()){
                editFormLeader=leader;
                redirectAttributes.addFlashAttribute("editModalMessage", "Leader's name already exists!");
                return  "redirect:/settings/Leaders";
            }else{

                boolean inCharge=leader.isInCharge();

                if(inCharge){
                    Long numOfIncharge=leaderService.countByInCharge();

                    if(numOfIncharge == 1){
                        editFormLeader=leader;
                        redirectAttributes.addFlashAttribute("editModalMessage", "There must be only one leader in charge!");
                        return "redirect:/settings/leaders";
                    }
                }

                leaderService.save(leader);
                editFormLeader=new Leader();
                redirectAttributes.addFlashAttribute("message", leader.getName() + " has been updated!");
            }

        }catch (Exception ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());

        }
        return "redirect:/settings/leaders";

    }

    @PostMapping("/leaders/changeLeader")
    public String updateLeader(@RequestParam("leaderName") String currentLeaderName,@RequestParam("name")String name, RedirectAttributes redirectAttributes){

        try{


            System.out.println(currentLeaderName);
            System.out.println(name);
            Optional<Leader> optionalCurrentLeader=leaderService.findLeaderByName(currentLeaderName);
            Optional<Leader> optionalLeader = leaderService.findLeaderByName(name);

            if( optionalCurrentLeader.isPresent() && optionalLeader.isPresent()){

                Leader leader=optionalLeader.get();
                if(leader.isInCharge()){
                    redirectAttributes.addFlashAttribute("changeModalMessage", "Already set as leader!");
                    return "redirect:/dashboard";
                }

               Long currentLeaderId=optionalCurrentLeader.get().getId();
               Long leaderId=leader.getId();

               leaderService.updateInCharge(currentLeaderId,leaderId);
               redirectAttributes.addFlashAttribute("message",leader.getName() + " has been assign as leader!");
            }else{
                throw new LeaderNotFoundException("Leader not found!");
            }

        }catch (Exception ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());

        }
        return "redirect:/dashboard";

    }


    @GetMapping("/leaders/delete/{id}")
    public String deleteLeader(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try{
            Optional<Leader> optionalLeader=leaderService.findById(id);

            if(optionalLeader.isPresent()){
                Leader leader=optionalLeader.get();

                String leaderName=leader.getName();
                leaderService.deleteById(id);
                redirectAttributes.addFlashAttribute("message", leaderName + " has been deleted.");
            }

        } catch (Exception ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
        }
        return "redirect:/settings/leaders";
    }

    @GetMapping("/leaders/{id}/inCharge/{inCharge}")
    public String updateLeaderInCharge(@PathVariable("id") Long id, @PathVariable("inCharge") boolean inCharge,
                                       RedirectAttributes redirectAttributes){
        try{

            Optional<Leader> leaderOptional=leaderService.findById(id);
            Leader leader;
            String name="";
            if(leaderOptional.isPresent()){
                leader=leaderOptional.get();
                name=leader.getName();
            }else{
                throw new LeaderNotFoundException("Leader with id no " + id + " not found!");
            }

                if(inCharge){
                    Long numOfIncharge=leaderService.countByInCharge();


                    if(numOfIncharge == 1){
                        redirectAttributes.addFlashAttribute("message", "There must be only one leader in charge!");
                        return "redirect:/settings/leaders";
                    }
                }



                leaderService.updateInCharge(id,inCharge);
                String status=inCharge?"assigned" : "unassigned";
                redirectAttributes.addFlashAttribute("message", String.format("%s has been %s as  leader!",name,status));

        }catch(Exception ex){
                redirectAttributes.addFlashAttribute("message", ex.getMessage());
                ex.printStackTrace();
        }

        return "redirect:/settings/leaders";
    }
}
