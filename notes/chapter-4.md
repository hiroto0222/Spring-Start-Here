
## 3. The Spring Context: Using Abstractions
#### Interface とは:
Interface は、クラスがどのようなメソッドを持っているのかをあらかじめ定義する、設計書のような存在。クラスとは異なり、具体的な処理内容を記述せず、メソッドの引数や戻り値だけを定義する。Contract（契約）を結ぶとも言う。

例として、配達されるパッケージの詳細を印刷する DeliveryDetailsPrinter オブジェクトを実装とする。詳細を印刷するメソッドは配送先住所で Sort される必要があり、その並べ替えの責任を SortByAddress のオブジェクトに委任するとする。この場合、DeliveryDetailsPrinter と SortByAddress は強く結合しているため、もし並べ替え方を変更する必要があった場合、様々な箇所の変更を加える必要がる。

```java
public class DeliveryDetailsPrinter {
  private SorterByAddress sorter; // <- ここ

  public DeliveryDetailsPrinter(SorterByAddress sorter) { // <- ここ
    this.sorter = sorter;
  }

  public void printDetails() {
    sorter.sortDetails(); // <- ここ
    // printing the delivery details
  }
}
```

インターフェースを使って decouple（抽象化）する。特定の実装に直接依存する代わりに、DeliveryDetailsPrinter はインターフェース（契約）に依存する。これにより、DeliveryDetailsPrinter は特定の実装に縛られることなく、このインターフェースを実装する任意のオブジェクトを使用できるようになる。

<p align="center">
<img width="600" src="../images/CH04_F03_Spilca2.png">
</p>

```java
public interface Sorter {
  void sortDetails();
}

public class DeliveryDetailsPrinter {
  private Sorter sorter;

  public DeliveryDetailsPrinter(Sorter sorter) {
    this.sorter = sorter;
  }

  public void printDetails() {
    sorter.sortDetails();
    // printing the delivery details
  }
}
```

#### フレームワークを使用せずに要件を実装してみる:
チームがタスクを管理するために使用するアプリを実装しているとする。このアプリで実装する機能として：
1. ユーザーがタスクにコメントを残すことができる。
   - ユースケースを実装するオブジェクトをサービスと呼ぶことが一般的
   - 「コメントを公開する」ユースケースを実装するサービスが必要であるため、```CommentService``` を作成する。
2. ユーザーがコメントを公開すると、それがデータベースに保存される。
   - データベースと直接やり取りをするオブジェクトを持つ場合、通常リポジトリと呼ぶ（時にはデータアクセスオブジェクト（DAO）と呼ばれることもある）。コメントを保存する責任を持つオブジェクト ```CommentRepository``` を作成する。
3. 特定のアドレスにメールが送信する。
   - アプリの外部と通信する責任を持つオブジェクトを実装する場合、それらのオブジェクトをプロキシと呼ぶ。メールを送信する責任を持つオブジェクト ```CommentNotificationProxy``` を作成する。

<p align="center">
<img width="600" src="../images/CH04_F06_Spilca2.png">
</p>

実装は [ex3](/exercises/ex3/) を参照。

#### Spring を使用して実装してみる:
