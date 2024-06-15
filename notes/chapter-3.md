
## 3. The Spring Context: Wiring Beans
Bean に依存関係を設定するには、主にメソッドの引数を使う。例として、「Person」が「Parrot」の飼い主であるという "has-a" 関係を表す時：

#### @Bean を使う場合:
```java
@Configuration
public class ProjectConfig {
 
  @Bean
  public Parrot parrot() {
    Parrot p = new Parrot();
    p.setName("Koko");
    return p;
  }
  
  // Person が依存する Parrot のインスタンスは、IoC コンテナによって与えられる。
  @Bean
  public Person person(Parrot parrot) { 
    Person p = new Person();
    p.setName("Ella");
    p.setParrot(parrot);
    return p;
  }
}
```

#### @Autowired を使う場合:
```@Autowired``` によるインジェクションの種類は以下三つ:
- Field injection（非推奨）
- Setter injection
- Constructor injection（推奨）

#### Field injection:
```java
@Component
public class Person {
  private String name;

  @Autowired
  private Parrot parrot;
 
  // ...
}
```

#### Setter injection:
```java
@Component
public class Person {
  private String name;
  private Parrot parrot;

  @Autowired
  public void setParrot(Parrot parrot) {
    this.parrot = parrot;
  }
 
  // ...
}
```

#### Constructor injection:
```java
@Component
public class Person {
  private String name;
  private final Parrot parrot;

  @Autowired
  public Person(Parrot parrot) {
    this.parrot = parrot;
  }
 
  // ...
}
```

### なぜ Constructor Injection が推奨されているか？
#### 1. 単一責任の原則（SOLID)
オブジェクト指向の言語における設計原則の一つ「SOLID原則」。
> SOLID stands for:  
S - Single-responsibility Principle  
O - Open-closed Principle  
L - Liskov Substitution Principle  
I - Interface Segregation Principle  
D - Dependency Inversion Principle  

簡単に言うと「1つのクラスは1つだけの責任（機能）を持たなければならない。」という思想。

Constructor injection が煩雑に感じる（記述が多く見える）場合、少なくともそのクラスは数多くの機能をインジェクションしている（＝依存関係が多い）ことになる。つまり単一責任の原則から見ると上記のようなクラスは「一つのクラスが多くの責任を持ちすぎている」ことになるため、原則に反している。Constructor injection を用いることでそのことに気づきやすくなる。

#### 2. Constructor が不変性を持てること
Constructor injection を行うことでフィールドを ```final``` で宣言することができる。
このことで immutable（状態を変更することができない）なオブジェクトにしたり、必要な依存関係のみを不変にすることができる。Field injection の場合、```final``` 宣言はできないため mutable なままとなる。

#### 3. 循環依存することを防げる
Constructor injection で ```final``` 宣言している場合、constructor 呼び出しのタイミングでDIの設定が完了し、その後は先述の通り immutable になる。そのため、循環依存が起きている場合、アプリケーション起動時に警告が出る。その他2つの injection については、実際に対象のDIコンテナが呼び出されるまでは問題を検知することができない。
