[![CircleCI branch](https://img.shields.io/circleci/project/github/leftshiftone/chartscript/master.svg?style=flat-square)](https://circleci.com/gh/leftshiftone/chartscript)
[![GitHub tag (latest SemVer)](https://img.shields.io/github/tag/leftshiftone/chartscript.svg?style=flat-square)](https://github.com/leftshiftone/chartscript/tags)
[![Maven Central](https://img.shields.io/maven-central/v/one.leftshift.chartscript/chartscript?style=flat-square)](https://mvnrepository.com/artifact/one.leftshift.chartscript/chartscript)

# ChartScript

ChartScript is an open source software library for script based chart generation. 
Each chart can be fully customized by the chartscript DSL. 

## PieChart

A pie chart definition can be started by the function **piechart**.

```kotlin
piechart{
   value("Java", 17)
   value("C/C++", 10)
   value("Python", 7)
   value("C#", 5)
   value("Javascript", 3)
}
```

The **piechart** function accepts a **style callback** in order to modify style settings.

```kotlin
piechart({backgroundPaint("blue")}){
   value("Java", 17)
   value("C/C++", 10)
   value("Python", 7)
   value("C#", 5)
   value("Javascript", 3)
}
```

The **style callback** accepts the following properties:

| signature                                   | Description                                                                                                  |
| ------------------------------------------- | ------------------------------------------------------------------------------------------------------------ |
| **title**(title:String)                     | Sets the title of the chart                                                                                  |
| **backgroundPaint**(paint:String)           | Sets the background paint via a css compliant color string                                                   |
| **labelBackgroundPaint**(paint:String)      | Sets the background paint of the labels via a css compliant color string                                     |
| **labelOutlinePaint**(paint:String)         | Sets the paint of the label outline via a css compliant color string                                         |
| **labelShadowPaint**(paint:String)          | Sets the paint of the label shadow via a css compliant color string                                          |
| **shadowPaint**(paint:String)               | Sets the paint of the chart shadow via a css compliant color string                                          |
| **isOutlineVisible**(visible:Boolean)       | Indicates if the chart outline is visible                                                                    |
| **borderPaint**(paint:String)               | Sets the paint of the chart border via a css compliant color string                                          |
| **labelPaint**(paint:String)                | Sets the paint of the labels via a css compliant color string                                                |
| **labelLinkStroke**(width:Number)           | Sets the stroke of the label link                                                                            |
| **labelOutlineStroke**(width:Number)        | Sets the stroke of the label outline                                                                         |
| **outlineStroke**(width:Number)             | Sets the stroke of the chart outline                                                                         |
| **borderStroke**(width:Number)              | Sets the stroke of the chart border                                                                          |
| **antiAlias**(b:Boolean)                    | Indicates if anti alias is enabled                                                                           |
| **isBorderVisible**(b:Boolean)              | Indicates if the border is visible                                                                           |
| **simpleLabels**(b:Boolean)                 | Indicates if labels are in the segments                                                                      |
| **labelFont**(font:String, size:Number)     | Sets the font for the labels                                                                                 |
| **legendPosition**(position:String)         | Sets the position of the legend. Possible values are "bottom", "left", "right" and "top"                     |
| **legendShape**(shape:String)               | Sets the shape of the legend items. Possible values are "box" and "circle"                                   |
| **legendFont**(font:String, size: Number)   | Sets the font of the legend items.                                                                           |
| **labelFormat**(format:String)              | Sets the format of the labels. Possible string placeholders are {0} (value), {1} (amount) and {2} (percent)  |

Each **value** of a piechart accepts a **value style callback** in order to modify value specific style settings.

```kotlin
piechart {
   value("Java", 17) {background("blue")}
   value("C/C++", 10)
   value("Python", 7)
   value("C#", 5)
   value("Javascript", 3)
}
```

The **value style callback** accepts the following properties:

| signature                          | Description                                                                                                  |
| -----------------------------------| ------------------------------------------------------------------------------------------------------------ |
| **background**(paint:String)       | Sets the background of the value slice via a css compliant color string                                      |
| **explodePercent**(percent:Number) | Sets the amount of space between the value slice and all other value slices                                  |

## BarChart

A bar chart definition can be started by the function **barchart**.

```kotlin
barchart("labelX", "labelY"){
   value(10, 15, 20)
   value(5, 17, 12)
   value(14, 19, 27)
}
```

The **barchart** function accepts a **style callback** in order to modify style settings.

```kotlin
barchart("labelX", "labelY", {vertical(false)}){
   value(10, 15, 20)
   value(5, 17, 12)
   value(14, 19, 27)
}
```

The **style callback** accepts the following properties:

| signature                                   | Description                                                                                                  |
| ------------------------------------------- | ------------------------------------------------------------------------------------------------------------ |
| **title**(title:String)                     | Sets the title of the chart                                                                                  |
| **backgroundPaint**(paint:String)           | Sets the background paint via a css compliant color string                                                   |
| **isOutlineVisible**(visible:Boolean)       | Indicates if the chart outline is visible                                                                    |
| **borderPaint**(paint:String)               | Sets the paint of the chart border via a css compliant color string                                          |
| **outlineStroke**(width:Number)             | Sets the stroke of the chart outline                                                                         |
| **borderStroke**(width:Number)              | Sets the stroke of the chart border                                                                          |
| **antiAlias**(b:Boolean)                    | Indicates if anti alias is enabled                                                                           |
| **isBorderVisible**(b:Boolean)              | Indicates if the border is visible                                                                           |
| **legendPosition**(position:String)         | Sets the position of the legend. Possible values are "bottom", "left", "right" and "top"                     |
| **legendFont**(font:String, size: Number)   | Sets the font of the legend items.                                                                           |
| **vertical**(b:Boolean)                     | Indicates if the plot orientation is vertical or horizontal                                                  |
| **seriesPaint**(series:Int, paint:String)   | Sets the paint of a series via a css compliant color string                                                  |

## Development

### Release
Releases are triggered locally. Just a tag will be pushed and CI pipelines take care of the rest.

#### Major
Run `./gradlew final -x sendReleaseEmail -Prelease.scope=major` locally.

#### Minor
Run `./gradlew final -x sendReleaseEmail -Prelease.scope=minor` locally.

#### Patch
Run `./gradlew final -x sendReleaseEmail -Prelease.scope=patch` locally.
