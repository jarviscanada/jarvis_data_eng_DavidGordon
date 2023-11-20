package ca.jrvs.apps.practice;

class HelloWorld {

    // Your program begins with a call to main().
    // Prints "Hello, World" to the terminal window.
    public static void main(String args[]) {
        RegexExcImpl rei = new RegexExcImpl();

        System.out.println(rei.matchJpeg("myimage.jpeg"));

        System.out.println(rei.matchIp("192.168.1.1"));

        System.out.println(rei.isEmptyLine("   x  "));
    }
}