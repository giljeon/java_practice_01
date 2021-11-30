package com.annuity;

public class NumberVO {

	// 조단위
	private int[] joNumberArray = {
			1,  //1
			3,	//2
			1,	//3
			5,	//4
			2	//5
	};

    // 1번
    private int[] oneNumberArray = {
			3,	//0
			6,	//1
			5,	//2
			0,	//3
			3,	//4
			3,	//5
			1,	//6
			2,	//7
			1,	//8
			0	//9
    };

	// 2번
	private int[] twoNumberArray = {
			1,	//0
			2,	//1
			2,	//2
			2,	//3
			2,	//4
			4,	//5
			3,	//6
			3,	//7
			2,	//8
			3	//9
	};

	// 3번
	private int[] threeNumberArray = {
			3,	//0
			2,	//1
			2,	//2
			3,	//3
			6,	//4
			1,	//5
			2,	//6
			2,	//7
			2,	//8
			1	//0
	};

	// 4번
	private int[] fourNumberArray = {
			4,	//0
			2,	//1
			4,	//2
			1,	//3
			3,	//4
			4,	//5
			4,	//6
			1,	//7
			0,	//8
			1	//0
	};

	// 5번
	private int[] fiveNumberArray = {
			2,	//0
			1,	//1
			1,	//2
			3,	//3
			1,	//4
			6,	//5
			3,	//6
			3,	//7
			1,	//8
			3	//9
	};

	// 6번
	private int[] sixNumberArray = {
			5,	//0
			0,	//1
			3,	//2
			3,	//3
			2,	//4
			3,	//5
			1,	//6
			2,	//7
			2,	//8
			3	//0
	};

	public int[] getJoNumberArray() { return this.joNumberArray;}
	public int[] getOneNumberArray() { return this.oneNumberArray;}
	public int[] getTowNumberArray() { return this.twoNumberArray;}
	public int[] getThreeNumberArray() { return this.threeNumberArray;}
	public int[] getFourNumberArray() { return this.fourNumberArray;}
	public int[] getFiveNumberArray() { return this.fiveNumberArray;}
	public int[] getSixNumberArray() { return this.sixNumberArray;}
}
