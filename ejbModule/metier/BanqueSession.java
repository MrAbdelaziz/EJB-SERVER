package metier;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import entities.Compte;

@Stateless(name="ok")
public class BanqueSession implements BanqueLocal, BanqueRemote{

	private Map<Integer, Compte> comptes = new HashMap<Integer, Compte>();
	
	@Override
	public void createCompte(Compte c) {
		comptes.put(c.getCode(), c);
	}

	@Override
	public Compte findCompteByCode(int code) {
		return comptes.get(code);
	}

	@Override
	public List<Compte> findListCompte() {
		return new ArrayList<Compte>(comptes.values());
	}

	@Override
	public void verser(int code, double montant) {
		Compte c = comptes.get(code);
		c.setSolde(c.getSolde() + montant);
	}

	@Override
	public void retirer(int code, double montant) {
		Compte c = comptes.get(code);
		if(c.getSolde() - montant < 0)
			throw new RuntimeException("solde insuffisant");
		c.setSolde(c.getSolde() - montant);
	}

	@PostConstruct
	public void init() {
		createCompte(new Compte(1, 100, new Date()));
		createCompte(new Compte(2, 3000, new Date()));
		createCompte(new Compte(3, 40500, new Date()));
		createCompte(new Compte(4, 3400, new Date()));
	}

	@Override
	public void verement(Compte c1, Compte c2, double montant) {
		this.retirer(c1.getCode(), montant);
		this.verser(c2.getCode(), montant);
	}
}
