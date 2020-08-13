package controller;


import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Teste {

	public static void main(String[] args) {

//		Aerodromo sbjr = new Aerodromo("sbjr", "Aeroporto de Jacarepaguá", 10,22, 59, 15, 's', 43, 22, 12, 'w');
//		Aerodromo sbmi = new Aerodromo("sbmi", "Aeroporto de Maricá", 5,22, 55, 05, 's', 42, 49, 44, 'w');
//		Fixo ronda = new Fixo("ronda",23, 4, 15, 's', 43, 11, 40, 'w');
//		Fixo fante = new Fixo("fante",23, 2, 26, 's', 43, 0, 20, 'w');
//		Fixo farol = new Fixo("farol",22, 59, 30, 's', 42, 41, 55, 'w');
//		Fixo saqua = new Fixo("SAQUA",22, 57, 27, 's', 42, 29, 56, 'w');
//		Fixo pjac1 = new Fixo("P.Jac.1",23,05,00,'s',43,22,12,'w');
//		Aerodromo sbcb = new Aerodromo("sbcb", "Aeroporto de Cabo Frio", 23,22, 55, 15, 's', 42, 4, 17, 'w');
//		Aeronave sts = new Aeronave();
//		sts.setPrefixo("pr-sts");
//		sts.setTipo("c150");
//		sts.setVelocidadeCruzeiro(75);
//		sts.setRazaoSubida(500);
//		sts.setRazaoDescida(500);
//		sts.setVelocidadeSubida(65);
//		sts.setVelocidadeDescida(75);
//		
//		Planejamento planejamento = new Planejamento();
//		planejamento.setOrigem(sbjr);
//		planejamento.setDestino(sbmi);
//		ArrayList<Fixo> rota = new ArrayList<Fixo>();
//		rota.add(pjac1);
//		planejamento.setRota(rota);
//		planejamento.setAltitude(5000);
//		planejamento.setAeronave(sts);
//		planejamento.calcular2();
//		ArrayList<Trecho> trechos = planejamento.getTrechos();
//		for(Trecho t: trechos) {
//			System.out.println(t.getInicio().toString() + " / " + t.getFim().toString() + " | " + t.getDistancia() + "nm | " + t.getRumo() + " | " + t.getTempoMinutos());
//		}
		Session session;
		try {
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			// pegando as configurações na session factory do arquivo hibernate.cfg.xml
			session = sessionFactory.openSession();
			// abrindo uma sessão/conexao
			session.close();
			// fechando a conexao
		} catch (HibernateException e) {
			System.out.println("Erro: " + e.toString());
		}
		
	}

}
