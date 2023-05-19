import 'package:flutter/material.dart';
import 'package:uber_clone_front/image_slider.dart';
import 'package:uber_clone_front/photo.dart';

class PhotosList extends StatelessWidget {
  const PhotosList({Key? key, required this.photos}) : super(key: key);

  final List<Photo> photos;

  @override
  Widget build(BuildContext context) {
    return GridView.builder(
      gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 1,
        childAspectRatio: 1.5,
        mainAxisSpacing: 0,
      ),
      itemCount: photos.length + 1,
      itemBuilder: (context, index) {
        if (index == 0) {
          return Column(
            children: [
              Container(
                height: 50,
                  margin:
                      const EdgeInsets.symmetric(vertical: 20, horizontal: 10),
                  alignment: Alignment.topLeft,
                  child: const Text("Category",
                      style: TextStyle(
                          fontWeight: FontWeight.bold, fontSize: 28))),
              Flexible(
                flex: 2,
                child: GridView.count(
                  crossAxisCount: 3,
                  padding: const EdgeInsets.all(10),
                  crossAxisSpacing: 10,
                  children: categoryTiles,
                ),
              ),
            ],
          );
        } else if (index == 2) {
          return Column(
            children: [
              Container(
                margin: const EdgeInsets.symmetric(horizontal: 10),
                alignment: Alignment.topLeft,
                child: const Text("Order again",
                    style:
                        TextStyle(fontWeight: FontWeight.bold, fontSize: 28)),
              ),
              const SizedBox(height: 8),
              Flexible(flex: 3, child: ImageSliderDemo(photos: photos)),
            ],
          );
        } else {
          return SizedBox(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                Container(
                  padding: const EdgeInsets.all(8.0),
                  height: 200,
                  constraints: const BoxConstraints(minWidth: 400),
                  child: ClipRRect(
                    borderRadius: BorderRadius.circular(5),
                    child: Image.network(
                      photos[index].url,
                      fit: BoxFit.cover,
                    ),
                  ),
                ),
                Text(photos[index].title)
              ],
            ),
          );
        }
      },
    );
  }

  List<Widget> get categoryTiles {
    return <Widget>[
      Column(
        children: [
          ClipRRect(
            borderRadius: const BorderRadius.all(Radius.circular(100)),
            child: Container(
              padding: const EdgeInsets.all(20),
              color: Colors.grey[300],
              child: const Icon(
                size: 50,
                Icons.restaurant,
                color: Colors.black87,
              ),
            ),
          ),
          const SizedBox(height: 10,),
          const Text("Restaurant")
        ],
      ),
      Column(
        children: [
          ClipRRect(
            borderRadius: const BorderRadius.all(Radius.circular(100)),
            child: Container(
              padding: const EdgeInsets.all(20),
              color: Colors.grey[300],
              child: const Icon(
                size: 50,
                Icons.local_grocery_store,
                color: Colors.black87,
              ),
            ),
          ),
          const SizedBox(height: 10,),
          const Text("Grocery")
        ],
      ),
      Column(
        children: [
          ClipRRect(
            borderRadius: const BorderRadius.all(Radius.circular(100)),
            child: Container(
              padding: const EdgeInsets.all(20),
              color: Colors.grey[300],
              child: const Icon(
                size: 50,
                Icons.more_horiz,
                color: Colors.black87,
              ),
            ),
          ),
          const SizedBox(height: 10,),
          const Text("See all")
        ],
      ),
    ];
  }
}
