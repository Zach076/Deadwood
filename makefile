compile: clean
	mkdir build
	cp ./board.xml ./build
	cp ./cards.xml ./build
	cp ./board.jpg ./build
	cp -r ./cards ./build
	cp -r ./dice ./build
	javac -d build src/**/*.java src/*.java
	
clean: 
		rm -rf build