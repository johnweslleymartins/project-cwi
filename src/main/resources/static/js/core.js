$(document).ready(function() {

    clearMessages();
    var accountData = [];
    loadAccountData();
  });

  function clearMessages(){
    $('#alert-success').hide();
    $('#alert-fail').hide();
    $('#alert-unexpected-fail').hide();
  }

  function executarJob(){

    $.get('http://localhost:8081/api/batch', function(mensagem, status){

        clearMessages();

        if('COMPLETED' === mensagem){
            $('#alert-success').show();
        } else if('FAILED' === mensagem){
            $('#alert-fail').show();
        }else{
            $('#alert-unexpected-fail').show();
        }
    });
  }

  function loadAccountData(){

    $('#table-account-data').find('tbody').each(function(){ this.remove();});

    $.get('http://localhost:8081/api/accountdata', function(result, status){

        accountData = result;

         $(accountData).each(
             
            function(){
              
                var $linha = $('<tr>');
                var $id = $('<td>');
                var $agencia = $('<td>');
                var $conta = $('<td>');
                var $saldo = $('<td>');
                var $status = $('<td>');
                var $dataCadastro = $('<td>');           
                var $processadoEnum = $('<td>');                  

                $agencia.append(this.agencia);
                $conta.append(this.conta);
                $saldo.append(this.saldo);
                $status.append(this.status);              

                $linha.append($agencia);
                $linha.append($conta);
                $linha.append($saldo);
                $linha.append($status);               

                $tableAccountData = $('#table-account-data');
                $tableAccountData.append($linha);
            })
    });
}