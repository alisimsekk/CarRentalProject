# Araç Kiralama Sistemi

Firmalar sisteme kayıt olup kiralamaya açık araçlarını kaydedir. Müşterileri de sisteme kaydolup müsait kiralık araçları filtreleyip kendisi için uygun olanı reverve eder.

## Kullanılan Teknolojiler

<code><img width="50" src="https://user-images.githubusercontent.com/25181517/117201156-9a724800-adec-11eb-9a9d-3cd0f67da4bc.png" alt="Java" title="Java"/></code>
<code><img width="50" src="https://user-images.githubusercontent.com/25181517/183896128-ec99105a-ec1a-4d85-b08b-1aa1620b2046.png" alt="MySQL" title="MySQL"/></code>
<code><img width="50" src="https://user-images.githubusercontent.com/25181517/192108890-200809d1-439c-4e23-90d3-b090cf9a4eea.png" alt="IntelliJ" title="IntelliJ"/></code>
<code><img width="50" src="https://user-images.githubusercontent.com/25181517/192108372-f71d70ac-7ae6-4c0d-8395-51d8870c2ef0.png" alt="Git" title="Git"/></code>
<code><img width="50" src="https://user-images.githubusercontent.com/25181517/192108374-8da61ba1-99ec-41d7-80b8-fb2f7c0a4948.png" alt="GitHub" title="GitHub"/></code>

## Özellikler

- Projede swing gui kullanılmıştır.
- Sisteme araç kiralayan firmalar kaydedilir.  
- Firmalar bulundukları şehri sisteme girer.  
- Firmalar kendilerine ait araçları sisteme kaydedebilir.  
  - Araçların günlük fiyatları ve fiyatların geçerli olduğu tarih aralıkları,  
  - Aracın tipi (binek, arazi , ticari araçlar vs.),  
  - Hangi tarihlerde müsait oldukları,
  - Varsa ek hizmetleri ve fiyatları tanımlanır.  
- Kullanıcılar sisteme kayıt olur.  
- Kullanıcılar sistemde şehirler üzerinden araç tipine ve tarihe göre uygun araçları listeleyebilir ve rezervasyon yapabilir.  
- Rezervasyon yapılan tarihte ilgili aracın müsait olması ve başka kiralama ile çakışmaması kontrol edilir.  
- Firmalar ve kullanıcılar yaptıkları rezervasyonları listeleyebilir.  
- Rezervasyonlar en erken 1 gün öncesinden iptal edilebilir.

## Ekran Görüntüleri
- Sign up ve Sign in ekranları
  <img src="src/images/sign-in.png" alt="Diagram_1" width="" />
  <img src="src/images/sign-up.png" alt="Diagram_1" width="" />
  

- Firma ana ekranı
  <img src="src/images/management-panel.png" alt="Diagram_1" width="" />
  

- Araç ekleme ekranı
  <img src="src/images/add-car.png" alt="Diagram_1" width="" />
  

- Kullanıcı ana ekranı
  <img src="src/images/customer-screen.png" alt="Diagram_1" width="" />
  

- Araç rezervasyon ekranı
  <img src="src/images/reservation-screen.png" alt="Diagram_1" width="" />



## Kurulum
1. Projeyi klonlayın.
    - https://github.com/alisimsekk/Car_Rental_Project.git
2. `src/com/helper/Config` sınıfında veri tabanı konfigürasyonunu yapın.
3. Projeyi ayağa kaldırmak için ideden start edin.


## Ortam Değişkenleri

Bu projeyi çalıştırmak için aşağıdaki ortam değişkenlerini Config sınıfından değiştirmelisiniz.

String DB_URL  
String DB_USERNAME  
String DB_PASSWORD

## Lisans

[MIT](https://choosealicense.com/licenses/mit/)