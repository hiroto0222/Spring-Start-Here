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
