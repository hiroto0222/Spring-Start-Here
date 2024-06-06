# Spring Start Here - Spilcă, Laurenţiu

## Table of Contents
1. [Spring In The Real World](#1-spring-in-the-real-world)
2. [The Spring Context: Defining Beans](#2-the-spring-context-defining-beans)
3. The Spring Context: Wiring Beans
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


