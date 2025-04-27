# dzsem fungorium TestProcessor

## Használat
```
java -jar target/TestProcessor-1.0.jar <assertfile> <outputfile>
```

## Tesztelési példa 1

**assert.txt**
```sh
d

# test2
i
```

**output.txt**
```sh
a
b
c
d
e
f
g
# asd
h
i
```

**command line**
```sh
# tfh. "TestProcessor" a working directory
mvn compile
mvn jar:jar

java -jar target/TestProcessor-1.0.jar assert.txt output.txt
```

**elvárt output**
```
SUCCESS
```


## Tesztelési példa 2
**assert2.txt**
```sh
d

# test2
i

# ÚJ SOR:
j
```

**output.txt**
<i>ld. [előző példa](#tesztelési-példa-1)</i>

**command line**
```sh
# tfh. "TestProcessor" a working directory
mvn compile
mvn jar:jar

java -jar target/TestProcessor-1.0.jar assert2.txt output.txt
```

**elvárt output**
```
FAIL
j
```