package com.who.helathy.fitplanner.activity

class ChartEnginePoint {
    var xValue: Float
    var xLabel: String
    var yValue: Float

    constructor(xV: Float, yV: Float) {
        this.xValue = xV
        this.yValue = yV
        this.xLabel = xV.toString()
    }

    constructor(xV: Float, yV: Float, xL: String) {
        this.xValue = xV
        this.yValue = yV
        this.xLabel = xL
    }
}