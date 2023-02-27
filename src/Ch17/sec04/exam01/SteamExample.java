package Ch17.sec04.exam01;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SteamExample {
	public static void main(String[] args) {
		// List컬렉션 생성
		List<Product> list = new ArrayList<>();

		for (int i = 1; i <= 5; i++) {
			Product product = new Product(i, "상품" + i, "멋진 회사", (int) (10000 * Math.random()));
			list.add(product);
		}

		Stream<Product> stream = list.stream();
		stream.forEach(p -> System.out.println(p));
	}
}
