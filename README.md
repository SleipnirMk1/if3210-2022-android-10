# IF3210-2022-Android-10
Tugas besar mata kuliah IF3210 Pengembangan Aplikasi Platform Khusus, Platform Android
## Deskripsi Aplikasi
Aplikasi berisi fitur-fitur yang membantu mengakses berita, mencari faskes, dan mempermudah berpergian selama masa pandemi
## Cara Kerja Aplikasi
* Aplikasi dapat mencari dan menampilkan daftar faskes berdasarkan daerah yang dipilih dengan memilih provinsi, kemudian kota/kabupaten
* Daftar faskes yang ditampilkan adalah 5 faskes yang terdekat dari lokasi pengguna
* Aplikasi dapat melihat detail dari faskes yang dipilih dari pencarian sebelumnya, seperti nama, kode, alamat, nomor telepon, jenis faskes, dan status kesiapannya
* Pada halaman yang sama, terdapat tombol untuk bookmark faskes tersebut atau menghilangkan bookmark
* Pada halaman ini juga dapat membuka lokasi faskes dengan google maps
* Aplikasi juga dapat membuka daftar faskes bookmark
* Aplikasi juga dapat memindai QR code untuk melakukan check in
* Saat check in, aplikasi akan mengirimkan kode dari QR code dan informasi latitude-longitude ke API, yang kemudian mengembalikan respon yang sesuai
* Terdapat 4 jenis respon, berhasil, gagal karena belum vaksin, bahaya karena positif covid-19, dan hati-hati
* Pada halaman check in juga ditampilkan suhu ruangan, tetapi hal ini tidak selalu tersedia karena tidak banyak device yang memiliki pengukur suhu sekitar
## Library yang Digunakan
* Retrofit : Untuk mengirimkan request ke API
* ZXing : Untuk barcode scanner yang memindai QR scanner
* Coroutines : Untuk melakukan pengambilan data secara asinkron
* Fragment : Untuk menampilkan recyclerview
* Location : Untuk memperoleh data lokasi pengguna
* Navigation : Untuk membuat navigation bar di bagian bawah
* Room : Untuk database yang menyimpan bookmark
* Glide: Untuk menampilkan gambar sebagai thumbnail berita
## Screenshot Aplikasi
## Pembagian Kerja
* Raffi Zulvian Muzhaffar (13519003)    : Berita COVID-19
* Jesica (13519011)                     : Cari Faskes, Detail Faskes, Bookmark Faskes, Bottom Navigation
* Ilyasa Salafi Putra Jamal (13519023)  : Menu Check-In

