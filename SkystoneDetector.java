// Have to add imports and packages seperately!
// Helpful video https://youtu.be/JO7dqzJi8lw
// Helping GitHub https://github.com/wolfcorpftc/SkystoneCVTutorial/tree/master/TeamCode/src/main/java/org/wolfcorp/cv/tutorial

public class SkystoneDetector extends OpenCvPipeline {
	private Telemetry telemetry;
	private mat = new Mat();
	public enum Location {
		LEFT,
		RIGHT,
		MIDDLE,
		NOT_FOUND
	}
	private Location location;
	static final Rect LEFT_ROI = new Rect(
		new Point(x:60, y:35), // Add and/or adjust coordinates here
		new Point(x:120, y:75)); // Add and/or adjust coordinates here
	/*static final Rect MIDDLE_ROI = new Rect(
		new Point(x:, y:), // Add and/or adjust coordinates here
		new Point(x:, y:)); // Add and/or adjust coordinates here*/
	static final Rect RIGHT_ROI = new Rect(
		new Point(x:140, y:35), // Add and/or adjust coordinates here
		new Point(x:200, y:75)); // Add and/or adjust coordinates here
		static double PERCENT_COLOR_THRESHOLD = 0.4;
	public SkystoneDetector(Telemetry t) {telemetry = t}


	@Override
	public Mat processFrame(Mat input) {
		Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
		Scalar lowHSV = new Scalar(23, 50, 70);
		Scalar highHSV = new Scalar(32, 255, 255);

		Core.inRange(mat, lowHSV, highHSV, mat);

		Mat left = mat.submat(LEFT_ROI);
		//Mat middle = mat.submat(MIDDLE_ROI);
		Mat right = mat.submat(RIGHT_ROI);

		double leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area() / 255;
		//double middleValue = Core.sumElems(middle).val[0] / MIDDLE_ROI.area() / 255;
		double rightValue = Core.sumElems(right).val[0] / RIGHT_ROI.area() / 255;

		left.release();
		//middle.release();
		right.release();

		telemetry.addData("Left raw Value", (int) Core.sumElems(left).val[0]);
		//telemetry.addData("Middle raw Value", (int) Core.sumElems(middle).val[0]);
		telemetry.addData("Right raw Value", (int) Core.sumElems(right).val[0]);
		telemetry.addData("Left percentage", Math.round(leftValue * 100) + "%");
		//telemetry.addData("Middle percentage", Math.round(middleValue * 100) + "%");
        telemetry.addData("Right percentage", Math.round(rightValue * 100) + "%");


		boolean stoneLeft = leftValue > PERCENT_COLOR_THRESHOLD;
		//boolean stoneMiddle = middleValue > PERCENT_COLOR_THRESHOLD;
		boolean stoneRight = rightValue > PERCENT_COLOR_THRESHOLD;

		if (stoneLeft && stoneRight /*&& stoneMiddle*/){
			location = Location.NOT_FOUND;
			telemetry.addData("Skystone Location", "Not Found");
		}
		if (stoneLeft) {
			location = Location.RIGHT;
			telemetry.addData("Skystone Location", "Right");
		//} else if (stoneRight) {
			//location = Location.LEFT
			//telemetry.addData("Skystone Location", "Left");
		} else {
			location = Location.LEFT;
			telemetry.addData("Skystone Location", "Left");
			//location = Location.MIDDLE;
			//telemetry.addData("Skystone Location", "Middle");
		}
		telemetry.update();

		Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB)

		Scalar colorStone = new Scalar(255,0,0);
		Scalar colorSkystone = new Scalar(0,255,0);

		Imgproc.rectangle(mat, LEFT_ROI, location == Location.LEFT? colorSkystone:colorStone);
        //Imgproc.rectangle(mat, MIDDLE_ROI, location == Location.MIDDLE? colorSkystone:colorStone)		
        Imgproc.rectangle(mat, RIGHT_ROI, location == Location.RIGHT? colorSkystone:colorStone)		

		return mat;

	}

	public Location getLocation(){
		return location;
	}

}