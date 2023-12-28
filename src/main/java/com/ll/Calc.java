package com.ll;

public class Calc {
  public static int run(String exp) { // 10 + (10 + 5)
    exp = exp.trim(); // 문자열 좌 우 공백 제거;
    exp = stripOuterBracket(exp); // 괄호 제거 후 exp return;

    // 연산기호가 없으면 바로 리턴
    if (!exp.contains(" ")) return Integer.parseInt(exp); // 만약 exp에 공백을 포함하지 않는다면 exp을 int로 바꾸고 return

    boolean needToMultiply = exp.contains(" * "); // exp에 * 연산자가 포함되 있다면 needToMultiply 변수에 넣고 ture 없으면 false
    boolean needToPlus = exp.contains(" + ") || exp.contains(" - "); // exp에 + 또는 -가 포함되 있다면 needToPlus 변수에 넣고 ture 없으면 false

    boolean needToCompound = needToMultiply && needToPlus; // needToMultipl 그리고 needToPlus가 참일 경우 needToCompound 변수에 넣고 ture
    boolean needToSplit = exp.contains("(") || exp.contains(")"); // '(' 또는 ')' 가 포함될 경우 needToSplit 변수에 넣고 ture

    if (needToSplit) {  // 800 + (10 + 5)  // 만약 needToSplit 라면

      int splitPointIndex = findSplitPointIndex(exp);   // findSplitPointIndex 함수에 exp 인자 넣고 그 값을 splitPointIndex 변수에 저장

      String firstExp = exp.substring(0, splitPointIndex);  // firstExp 변수에 지정된 위치의 문자열을 잘라 넣는다.
      String secondExp = exp.substring(splitPointIndex + 1); // secondExp 변수에 지정된 위치의 문자열 + 1 을 잘라 넣는다.

      char operator = exp.charAt(splitPointIndex); // char 타입만 넣을 수 있는 변수 operator에 원하는 위치의 연산자를 잘라 char 타입으로 변한 후 넣는다.

      exp = Calc.run(firstExp) + " " + operator + " " + Calc.run(secondExp); // exp을 Calc.run(firstExp) + " " + operator + " " + Calc.run(secondExp)로 초기화

      return Calc.run(exp); // Calc class run 메소드에 exp값 리턴한다.

    } else if (needToCompound) {                  //만약 needToCompound 라면
      String[] bits = exp.split(" \\+ ");  // String 객체를 연결할 수 있는 변수 bits에 + 연산자를 기준으로 split한 문자열을 담는다.

      return Integer.parseInt(bits[0]) + Calc.run(bits[1]); // TODO
    } // 객체 bits 0번째에 있는 문자열을
    if (needToPlus) {
      exp = exp.replaceAll("\\- ", "\\+ \\-");

      String[] bits = exp.split(" \\+ ");

      int sum = 0;

      for (int i = 0; i < bits.length; i++) {
        sum += Integer.parseInt(bits[i]);
      }

      return sum;
    } else if (needToMultiply) {
      String[] bits = exp.split(" \\* ");

      int rs = 1;

      for (int i = 0; i < bits.length; i++) {
        rs *= Integer.parseInt(bits[i]);
      }
      return rs;
    }

    throw new RuntimeException("처리할 수 있는 계산식이 아닙니다");
  }

  private static int findSplitPointIndexBy(String exp, char findChar) {
    int bracketCount = 0; // bracketCount에 정수 0 초기화

    for (int i = 0; i < exp.length(); i++) {       // i = 0초기화 i는 작다  exp의 문자열 길이보다  i는 1씩 증가한다.
      char c = exp.charAt(i);

      if (c == '(') {
        bracketCount++;
      } else if (c == ')') {
        bracketCount--;
      } else if (c == findChar) {
        if (bracketCount == 0) return i;
      }
    }
    return -1;
  }

  private static int findSplitPointIndex(String exp) {
    int index = findSplitPointIndexBy(exp, '+'); // findSplitPointIndexBy 실행하고 괄호 안 인자 넣기

    if (index >= 0) return index; // 만약 인덱스가 0보다 크거나 같으면 인덱스 값을 리턴

    return findSplitPointIndexBy(exp, '*'); // findSplitPointIndexBy에 exp, * 값 리턴
  }

  private static String stripOuterBracket(String exp) {
    int outerBracketCount = 0;

    while (exp.charAt(outerBracketCount) == '(' && exp.charAt(exp.length() - 1 - outerBracketCount) == ')') {
      outerBracketCount++;
    }

    if (outerBracketCount == 0) return exp;


    return exp.substring(outerBracketCount, exp.length() - outerBracketCount);
  }
}