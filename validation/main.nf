
process sayHello {
    input:
    val name
    output:
    stdout

    script:
    """
    echo '$name'
    """
}

workflow{
    channel.of('jorge','raffaella') | sayHello | view
}