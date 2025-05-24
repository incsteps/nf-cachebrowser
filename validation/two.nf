
process sayHello {
    input:
    val name
    output:
    stdout

    script:
    """
    echo hi $name
    """
}

workflow{
    channel.of('jorge','raffaella') | sayHello | view
}