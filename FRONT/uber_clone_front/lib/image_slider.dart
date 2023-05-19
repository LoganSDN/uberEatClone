import 'package:flutter/material.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:uber_clone_front/photo.dart';

class ImageSliderDemo extends StatelessWidget {
  final List<Photo> photos;

  const ImageSliderDemo({Key? key, required this.photos}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final List<String> imgList = photos.map((photo) => photo.url).toList();

    return Scaffold(
      body: CarouselSlider(
        options: CarouselOptions(),
        items: imgList
            .asMap()
            .entries
            .map((entry) => buildImageSliderItem(entry.value, entry.key))
            .toList(),
      ),
    );
  }

  Widget buildImageSliderItem(String imageUrl, int index) {
    return Container(
      margin: const EdgeInsets.all(5.0),
      child: ClipRRect(
        borderRadius: const BorderRadius.all(Radius.circular(5.0)),
        child: Stack(
          children: <Widget>[
            Image.network(imageUrl, fit: BoxFit.cover, width: 1000.0),
            Positioned(
              bottom: 0.0,
              left: 0.0,
              right: 0.0,
              child: Container(
                decoration: const BoxDecoration(
                  gradient: LinearGradient(
                    colors: [
                      Color.fromARGB(200, 0, 0, 0),
                      Color.fromARGB(0, 0, 0, 0)
                    ],
                    begin: Alignment.bottomCenter,
                    end: Alignment.topCenter,
                  ),
                ),
                padding: const EdgeInsets.symmetric(
                    vertical: 10.0, horizontal: 20.0),
                child: Text(
                  photos[index].title,
                  style: const TextStyle(
                    color: Colors.white,
                    fontSize: 20.0,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
