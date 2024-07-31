import java.util.Scanner;

abstract class Conta {
    protected double saldo;
    private String titular;
    private int numeroConta;

    public Conta(String titular, int numeroConta, double saldoInicial) {
        this.titular = titular;
        this.numeroConta = numeroConta;
        this.saldo = saldoInicial;
    }

    public abstract void imprimirInformacoes();

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("Deposito realizado com sucesso!");
        } else {
            System.out.println("Valor de depósito inválido.");
        }
    }

    public boolean sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque realizado com sucesso!");
            return true;
        } else {
            System.out.println("Saldo insuficiente ou valor inválido.");
            return false;
        }
    }

    public boolean transferir(Conta destino, double valor) {
        if (sacar(valor)) {
            destino.depositar(valor);
            System.out.println("Transferência realizada com sucesso!");
            return true;
        } else {
            System.out.println("Transferência falhou.");
            return false;
        }
    }

    public double getSaldo() {
        return saldo;
    }

    public String getTitular() {
        return titular;
    }

    public int getNumeroConta() {
        return numeroConta;
    }
}

class ContaCorrente extends Conta {
    private double limite;

    public ContaCorrente(String titular, int numeroConta, double saldoInicial, double limite) {
        super(titular, numeroConta, saldoInicial);
        this.limite = limite;
    }

    @Override
    public void imprimirInformacoes() {
        System.out.printf("Conta Corrente%nTitular: %s%nNumero: %d%nSaldo: R$ %.2f%nLimite: R$ %.2f%n",
                getTitular(), getNumeroConta(), getSaldo(), limite);
    }
}

class ContaPoupanca extends Conta {
    private double taxaJuros;

    public ContaPoupanca(String titular, int numeroConta, double saldoInicial, double taxaJuros) {
        super(titular, numeroConta, saldoInicial);
        this.taxaJuros = taxaJuros;
    }

    @Override
    public void imprimirInformacoes() {
        System.out.printf("Conta Poupança%nTitular: %s%nNumero: %d%nSaldo: R$ %.2f%nTaxa de Juros: %.2f%%%n",
                getTitular(), getNumeroConta(), getSaldo(), taxaJuros);
    }
}

public class Banco {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Leitura das contas
        System.out.println("Digite os dados da primeira conta:");
        System.out.print("Titular: ");
        String titular1 = scanner.nextLine();
        System.out.print("Numero da Conta: ");
        int numeroConta1 = scanner.nextInt();
        System.out.print("Saldo Inicial: ");
        double saldoInicial1 = scanner.nextDouble();
        System.out.print("Limite: ");
        double limite = scanner.nextDouble();
        scanner.nextLine(); // Limpar o buffer do scanner

        ContaCorrente contaCorrente = new ContaCorrente(titular1, numeroConta1, saldoInicial1, limite);

        System.out.println("Digite os dados da segunda conta:");
        System.out.print("Titular: ");
        String titular2 = scanner.nextLine();
        System.out.print("Numero da Conta: ");
        int numeroConta2 = scanner.nextInt();
        System.out.print("Saldo Inicial: ");
        double saldoInicial2 = scanner.nextDouble();
        System.out.print("Taxa de Juros: ");
        double taxaJuros = scanner.nextDouble();
        scanner.nextLine(); // Limpar o buffer do scanner

        ContaPoupanca contaPoupanca = new ContaPoupanca(titular2, numeroConta2, saldoInicial2, taxaJuros);

        // Exibição das informações
        System.out.println("\nInformações da Conta Corrente:");
        contaCorrente.imprimirInformacoes();

        System.out.println("\nInformações da Conta Poupança:");
        contaPoupanca.imprimirInformacoes();

        // Operações
        System.out.println("\nOperações:");
        System.out.print("Digite o valor do depósito na Conta Corrente: ");
        double valorDeposito = scanner.nextDouble();
        contaCorrente.depositar(valorDeposito);

        System.out.print("Digite o valor do saque na Conta Poupança: ");
        double valorSaque = scanner.nextDouble();
        contaPoupanca.sacar(valorSaque);

        System.out.print("Digite o valor da transferência da Conta Corrente para a Conta Poupança: ");
        double valorTransferencia = scanner.nextDouble();
        contaCorrente.transferir(contaPoupanca, valorTransferencia);

        // Fechar o scanner
        scanner.close();
    }
}
