# Spring Start Here - Spilcă, Laurenţiu

## Table of Contents
1. [Spring In The Real World](#1-spring-in-the-real-world)
2. [The Spring Context: Defining Beans](#2-the-spring-context-defining-beans)
3. [The Spring Context: Wiring Beans](#3-the-spring-context-wiring-beans)
4. The Spring Context: Using Abstractions
5. The Spring Context: Bean Scopes And Life Cycle
6. Using Aspects with Spring AOP
7. Understanding Spring Boot And Spring MVC
8. Implementing Web Apps With Spring Boot And Spring MVC
9. Using The Spring Web Scopes
10. Implementing REST Services
11. Consuming REST Endpoints
12. Using Data Sources In Spring Apps
13. Using Transactions In Spring Apps
14. Implementing Data Persistence With Spring Data
15. Testing Your Spring App


## 1. Spring In The Real World
### The Spring ecosystem
Spring は様々なフレームワークのエコシステムであり、主に以下を指す:
1. Spring Core  
IoC (inversion of control) = 制御の反転  
ライブラリはアプリケーションから制御するが、フレームワークはアプリケーションを制御する。  
IoC コンテナ → Spring Context
1. Spring model-view-controller (MVC)
2. Spring Data Access
3. Spring testing

広大な Spring Framework の世界において、その中心・底になるのが IoC コンテナ（Spring Context）。
> https://kakutani.com/trans/fowler/injection.html
DI (Dependency Injection) は IoC の一種である。Spring の IoC コンテナが提供しているのは DI、Spring において IoC コンテナ = DI コンテナと言ってよさそう。


## 2. The Spring Context: Defining Beans
### Maven とは？
プロジェクトのビルド、テスト、ドキュメンテーション、成果物の配備など、プロジェクトのライフサイクル全体を管理するもの。プロジェクトに関する色々な情報を POM に集約し、POM の情報に基づいてプロジェクト全体を管理する。yarn 的なと思いつつプロジェクトの依存するライブラリの管理以上ができるみたい。
> Introducing Maven: A Build Tool for Today’s Java Developers by Balaji Varanasi (APress, 2019).

### Bean とは？
Spring の IoC コンテナによって管理されるオブジェクトを Bean と呼ぶ。Spring の設定とは Bean の設定のことを指す。
- どのような Bean を定義するか
- Bean にどのようなプロパティを与えるか
- Bean をどのように初期化するか
- Bean をどのように破棄するか
- どの Bean と Bean をつなげるか

といった設定を IoC コンテナに渡すと、IoC コンテナは Bean のオブジェクトツリーを構築し、適切に生成/破棄を行う。

### コンテナの表現
Spring の IoC コンテナは ApplicationContext インタフェースで表現されている。実装のバリエーションはいくつもある。

設定の読み込み、Bean の管理、依存関係の解決などは ApplicationContext によって提供される。Spring の中心には常に ApplicationContext がある。

厳密には、Bean の管理と依存関係の解決は BeanFactory によって提供される。ApplicationContext は BeanFactory のスーパーセットである。よほど特別な理由がない限り BeanFactory を直接使うことはなさそう。

### Java-Based Configuration
基本となるのは ```@Bean``` と ```@Configuration``` である。

#### @Configuration
```@Configuration``` はクラスにつけるアノテーションである。そのクラスが設定を主としたクラスであることを、IoC コンテナに知らせるためのマーカーである。
```java
@Configuration
public class Config {
  // ...
}
```

#### @Bean
```@Bean``` はメソッドにつけるアノテーションである。メソッドが Bean 定義であることを IoC コンテナに知らせるためのマーカーである。メソッドの戻り値は IoC コンテナによって管理される Bean となる。

```java
@Configuration
public class ProjectConfig {
    @Bean
    Parrot parrot() {
        var p = new Parrot();
        p.setName("Koko");
        return p;
    }
}
```
Javaのベストプラクティスとして、メソッドはアクションを表すため、通常メソッド名には動詞が含まれるが、Spring Context に Bean を追加するために使用するメソッドに関しては、この規約に従わない。これらのメソッドは、返されるオブジェクトインスタンスを表し、Spring コンテキストの一部となる。また、メソッドの名前が Bean の名前にもなる（この場合 Bean の名前が「parrot」になる）。

#### @Component
```@Component``` はクラスにつけるアノテーションである。このアノテーションをつけたクラスが、IoC コンテナの管理対象であることを知らせるためのマーカーである。```@Component``` がついたクラスは ```@Bean``` による Bean 定義を書かずとも、IoC コンテナの管理対象になる。同じような役割の Bean が大量にある場合には、いちいち ```@Bean``` で Bean を定義していくよりも手軽。

```java
@Component
public class Parrot {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

```@Component``` はクラスだけでなくアノテーションにもつけることができる (= メタアノテーション)。```@Component``` がついたアノテーションをつけたクラスもまた、IoC コンテナの管理対象として扱われる。```@Component``` がついたアノテーションはステレオタイプと呼ばれたりもする。

典型的なコンポーネントとして、3 つのステレオタイプが用意されている:

- ```@Controller```
  - Web アプリケーションのコントローラクラスであることを表す 
- ```@Service```
  - サービス層のクラスであることを表す
- ```@Repository```
  - 永続層のクラスであることを表す


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
