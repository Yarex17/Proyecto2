package Domain;

public class Equipo {

	private Castillo castillo;
	private Catapulta catapulta;

	public Equipo(Castillo castillo, Catapulta catapulta) {
		this.castillo = castillo;
		this.catapulta = catapulta;
	}

	public Castillo getCastillo() {
		return castillo;
	}

	public void setCastillo(Castillo castillo) {
		this.castillo = castillo;
	}

	public Catapulta getCatapulta() {
		return catapulta;
	}

	public void setCatapulta(Catapulta catapulta) {
		this.catapulta = catapulta;
	}

}
