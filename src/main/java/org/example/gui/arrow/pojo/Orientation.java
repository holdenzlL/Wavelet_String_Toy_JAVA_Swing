package org.example.gui.arrow.pojo;

public enum Orientation {
    lowerRight, // First Quadrant: x > 0, y > 0
    lowerLeft,  // Second Quadrant: x < 0, y > 0
    upperLeft,  // Third Quadrant: x < 0, y < 0
    upperRight, // Forth Quadrant: x > 0, y < 0
    vertical,
    horizontal;
}
