package com.emlynoregan.apps.intrinsicalpha.classes;

public enum WildType {
	Learn,
	Create,
	Explore,
	PractiseBiWeekly,
	PractiseWeekly,
	PractiseFortnightly,
	PractiseMonthly;
	
	public int repetitionValueDays(int aRepetitionNumber)
	{	
		int retval = 0;
		
		if (aRepetitionNumber > 0)
		{
			if (this == Learn || this == Create || this == Explore)
			{
				retval = fib(aRepetitionNumber);
			}
			else if (this == PractiseBiWeekly)
			{
				retval = (aRepetitionNumber % 2 == 0 ? 3 : 4);
			}
			else if (this == PractiseWeekly)
			{
				retval = 7;
			}
			else if (this == PractiseFortnightly)
			{
				retval = 14;
			}
			else if (this == PractiseMonthly)
			{
				retval = 30;
			}
		}
		
		return retval;
	}
	
	private static int fib(int n) {
        int prev1=0, prev2=1;
        for(int i=0; i<n; i++) {
            int savePrev1 = prev1;
            prev1 = prev2;
            prev2 = savePrev1 + prev2;
        }
        return prev1;
	}	
}
