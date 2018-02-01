package santa.santa2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import santa.SantaIssue;
import santa.common.objects.Coordinates;

public class Santa2017_20 implements SantaIssue {

	int idCount = 0;
	List<Characteristics> particles = null;

	@Override
	public void solveBothParts(String data, List<String> dataLines) {

		particles = dataLines.stream().map(i -> i.split(","))
				.map(i -> new Characteristics(
						new Coordinates(Integer.parseInt(i[0].substring(3)), Integer.parseInt(i[1]),
								Integer.parseInt(i[2].substring(0, i[2].length() - 1))),
						new Coordinates(Integer.parseInt(i[3].substring(4)), Integer.parseInt(i[4]),
								Integer.parseInt(i[5].substring(0, i[5].length() - 1))),
						new Coordinates(Integer.parseInt(i[6].substring(4)), Integer.parseInt(i[7]),
								Integer.parseInt(i[8].substring(0, i[8].length() - 1)))))
				.collect(Collectors.toList());

		solvePart1();
		solvePart2();
	}

	public void solvePart1() {

		Characteristics minDistParticle = null;
		for (int j = 0; j < 50000; j++) {
			long min = Long.MAX_VALUE;
			minDistParticle = null;
			for (Characteristics particle : particles) {
				particle.changeVelocity();
				particle.changePosition();
				long dist = particle.getDistance();
				if (dist < min) {
					min = dist;
					minDistParticle = particle;
				}
			}
		}

		System.out.println(minDistParticle);
	}

	public void solvePart2() {

		for (int j = 0; j < 1000; j++) {
			Map<Coordinates, List<Characteristics>> sameDistance = new HashMap<Coordinates, List<Characteristics>>();

			for (Characteristics particle : particles) {
				particle.changeVelocity();
				particle.changePosition();

				if (sameDistance.containsKey(particle.getPosition())) {
					sameDistance.get(particle.getPosition()).add(particle);
				} else {
					List<Characteristics> list = new ArrayList<>();
					list.add(particle);
					sameDistance.put(particle.getPosition(), list);
				}
			}

			for (Coordinates key : sameDistance.keySet()) {
				if (sameDistance.get(key).size() > 1) {
					for (Characteristics particle : sameDistance.get(key)) {
						particles.remove(particle);
					}
				}
			}

		}

		System.out.println(particles.size());
	}

	private class Characteristics {
		private int id;
		private Coordinates position;
		private Coordinates velocity;
		private Coordinates acceleration;

		public Characteristics(Coordinates position, Coordinates velocity, Coordinates acceleration) {
			this.position = position;
			this.velocity = velocity;
			this.acceleration = acceleration;
			this.id = idCount++;
		}

		public void changeVelocity() {
			velocity.setX(velocity.getX() + acceleration.getX());
			velocity.setY(velocity.getY() + acceleration.getY());
			velocity.setZ(velocity.getZ() + acceleration.getZ());
		}

		public void changePosition() {
			position.setX(position.getX() + velocity.getX());
			position.setY(position.getY() + velocity.getY());
			position.setZ(position.getZ() + velocity.getZ());
		}

		public long getDistance() {
			return Math.abs(position.getX()) + Math.abs(position.getY()) + Math.abs(position.getZ());
		}

		@Override
		public String toString() {
			return "Characteristics [id=" + id + ", position=" + position + ", velocity=" + velocity + ", acceleration="
					+ acceleration + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + id;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Characteristics other = (Characteristics) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (id != other.id)
				return false;
			return true;
		}

		private Santa2017_20 getOuterType() {
			return Santa2017_20.this;
		}

		public Coordinates getPosition() {
			return position;
		}

	}
}
