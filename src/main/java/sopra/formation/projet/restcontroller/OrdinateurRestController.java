package sopra.formation.projet.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import sopra.formation.projet.model.Ordinateur;
import sopra.formation.projet.service.OrdinateurService;

@RestController
@RequestMapping("/rest/materiel/ordinateur")
@CrossOrigin(origins = {"*"})
public class OrdinateurRestController {

	@Autowired
	private OrdinateurService ordinateurService;
	
	@GetMapping(path= { "" , "/" })
	public ResponseEntity<List<Ordinateur>> findAllOrdinateur(){
		return new ResponseEntity<>(ordinateurService.showAllOrdinateur(), HttpStatus.OK);
	}
	
	@PostMapping(path= { "" , "/" })
	public ResponseEntity<Void> createOrdinateur(@Valid @RequestBody Ordinateur ordinateur, BindingResult result, UriComponentsBuilder uCB){
		ResponseEntity<Void> response = null;
		if(result.hasErrors()) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			ordinateurService.createOrdinateur(ordinateur);
			HttpHeaders header = new HttpHeaders();
			header.setLocation(uCB.path("/rest/materiel/ordinateur/{id}").buildAndExpand(ordinateur.getId()).toUri());
			response = new ResponseEntity<>(header, HttpStatus.CREATED);
		}
		return response;
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Ordinateur> findById(@PathVariable(name="id") Integer id) {
		Ordinateur ordinateur = ordinateurService.showOrdinateurById(id);
		ResponseEntity<Ordinateur> response = null;
		if(ordinateur != null) {
			response = new ResponseEntity<Ordinateur>(ordinateur, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return response;
	}
	
	@PutMapping(path= { "" , "/" })
	public ResponseEntity<Ordinateur> update(@Valid @RequestBody Ordinateur ordinateur, BindingResult result){
		ResponseEntity<Ordinateur> response = null;
		if(result.hasErrors() || ordinateur.getId() == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		} else {
			Ordinateur ordinateurEnBase = ordinateurService.showOrdinateurById(ordinateur.getId());
			ordinateurEnBase.setCode(ordinateurEnBase.getDisqueDur());
			ordinateurEnBase.setRam(ordinateurEnBase.getRam());
			ordinateurEnBase.setProcesseur(ordinateurEnBase.getProcesseur());
			ordinateurEnBase.setAchatOrdi(ordinateurEnBase.getAchatOrdi());
			response = new ResponseEntity<Ordinateur>(ordinateurEnBase, HttpStatus.OK);

		}
		return response;
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable(name="id") Integer id){
		Ordinateur ordinateur = ordinateurService.showOrdinateurById(id);
		ResponseEntity<Void> response = null;
		if(ordinateur != null) {
			ordinateurService.deleteOrdinateur(ordinateur);
			response = new ResponseEntity<>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return response;
	}
}