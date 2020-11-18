/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.pai_lab5.controllers;

import com.rl.pai_lab5.beans.Pracownik;
import com.rl.pai_lab5.dao.PracownikDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PracownikController {

	@Autowired
	PracownikDao dao; //wstrzyknięcie dao z pliku XML

	/* Wynikiem działania metody jest wyświetlenie formularza do wprowadzania
   danych, a „command” jest zastrzeżonym atrybutem żądania, który służy do
   wyświetlania danych obiektu w formularzu.
	 */
	@RequestMapping("/addForm")
	public String showform(Model m) {
		m.addAttribute("command", new Pracownik());
		return "addForm";
	}

	/* Metoda obsługuje zapis pracownika do BD. @ModelAttribute umieszcza dane
	* z żądania w obiekcie modelu. Jawnie wskazano tu metodę RequestMethod.POST,
	* ponieważ domyślnie jest to metoda GET
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("pr") Pracownik pr) {
		dao.save(pr);			
		return "redirect:/viewAll";//przekierowanie do widoku /viewAll
	}

	/* Metoda pobiera listę pracowników z BD i umieszcza je w modelu */
	@RequestMapping("/viewAll")
	public String viewAll(Model m) {
		List<Pracownik> list = dao.getAll();
		m.addAttribute("list", list);
		return "viewAll";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model m) {
		if (id > 0) {
			try {
				m.addAttribute("command", dao.getPracownikById(id));
				return "editForm";				
			}
			catch (EmptyResultDataAccessException e) {
				 e.printStackTrace();
			}
		}
		return "redirect:/errorPage.jsp";
	}
	
	@RequestMapping(value = "/editSave", method = RequestMethod.POST)
	public String editSave(@ModelAttribute("pr") Pracownik pr) {
		dao.update(pr);
		return "redirect:/viewAll";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		if (id > 0) {
			try {
				dao.delete(id);			
			}
			catch (EmptyResultDataAccessException e) {
				 e.printStackTrace();
				 return "redirect:errorPage.jsp";
			}
		}
		
		return "redirect:/viewAll";
	}
}
