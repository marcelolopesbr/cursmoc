-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Tempo de geração: 14/11/2019 às 20:55
-- Versão do servidor: 5.6.46
-- Versão do PHP: 7.2.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `curso_spring`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `categoria`
--

CREATE TABLE `categoria` (
  `id` int(11) NOT NULL,
  `nome` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Despejando dados para a tabela `categoria`
--

INSERT INTO `categoria` (`id`, `nome`) VALUES
(1, 'Informática'),
(2, 'Escritório'),
(3, 'Cama, mesa e banho'),
(4, 'Utilidades domésticas'),
(5, 'Eletrônicos'),
(6, 'Brinquedos'),
(7, 'Vestuário');

-- --------------------------------------------------------

--
-- Estrutura para tabela `cidade`
--

CREATE TABLE `cidade` (
  `id` int(11) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `estado_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Despejando dados para a tabela `cidade`
--

INSERT INTO `cidade` (`id`, `nome`, `estado_id`) VALUES
(1, 'Uberlândia', 1),
(2, 'Campinas', 2),
(3, 'São Paulo', 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `cliente`
--

CREATE TABLE `cliente` (
  `id` int(11) NOT NULL,
  `cpf_ou_cnpj` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `tipo` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Despejando dados para a tabela `cliente`
--

INSERT INTO `cliente` (`id`, `cpf_ou_cnpj`, `email`, `nome`, `tipo`) VALUES
(1, '27227800032', 'eita@eita.com', 'Maria das Dores', 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `endereco`
--

CREATE TABLE `endereco` (
  `id` int(11) NOT NULL,
  `bairro` varchar(255) DEFAULT NULL,
  `cep` varchar(255) DEFAULT NULL,
  `complemento` varchar(255) DEFAULT NULL,
  `logradouro` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `cidade_id` int(11) DEFAULT NULL,
  `cliente_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Despejando dados para a tabela `endereco`
--

INSERT INTO `endereco` (`id`, `bairro`, `cep`, `complemento`, `logradouro`, `numero`, `cidade_id`, `cliente_id`) VALUES
(1, 'Oraculo', '3484787', 'Ap 301', 'Rua Java', '100', 1, 1),
(2, 'Longe', '387644', 'casa 12', 'Rua Fim do Mundo', '400', 3, 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `estado`
--

CREATE TABLE `estado` (
  `id` int(11) NOT NULL,
  `nome` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Despejando dados para a tabela `estado`
--

INSERT INTO `estado` (`id`, `nome`) VALUES
(1, 'Minas Gerais'),
(2, 'São Paulo');

-- --------------------------------------------------------

--
-- Estrutura para tabela `item_pedido`
--

CREATE TABLE `item_pedido` (
  `desconto` double DEFAULT NULL,
  `preco` double DEFAULT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `pedido_id` int(11) NOT NULL,
  `produto_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Despejando dados para a tabela `item_pedido`
--

INSERT INTO `item_pedido` (`desconto`, `preco`, `quantidade`, `pedido_id`, `produto_id`) VALUES
(0, 4000, 2, 1, 1),
(0, 800, 1, 1, 2),
(10, 70, 1, 2, 3);

-- --------------------------------------------------------

--
-- Estrutura para tabela `pagamento`
--

CREATE TABLE `pagamento` (
  `pedido_id` int(11) NOT NULL,
  `estado_pagamento` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Despejando dados para a tabela `pagamento`
--

INSERT INTO `pagamento` (`pedido_id`, `estado_pagamento`) VALUES
(1, 1),
(2, 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `pagamento_com_boleto`
--

CREATE TABLE `pagamento_com_boleto` (
  `data_pagamento` datetime DEFAULT NULL,
  `data_vencimento` datetime DEFAULT NULL,
  `pedido_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Despejando dados para a tabela `pagamento_com_boleto`
--

INSERT INTO `pagamento_com_boleto` (`data_pagamento`, `data_vencimento`, `pedido_id`) VALUES
(NULL, '2019-11-09 02:01:00', 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `pagamento_com_cartao`
--

CREATE TABLE `pagamento_com_cartao` (
  `numero_de_parcelas` int(11) DEFAULT NULL,
  `pedido_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Despejando dados para a tabela `pagamento_com_cartao`
--

INSERT INTO `pagamento_com_cartao` (`numero_de_parcelas`, `pedido_id`) VALUES
(3, 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `pedido`
--

CREATE TABLE `pedido` (
  `id` int(11) NOT NULL,
  `instant` datetime DEFAULT NULL,
  `cliente_id` int(11) DEFAULT NULL,
  `endereco_de_entrega_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Despejando dados para a tabela `pedido`
--

INSERT INTO `pedido` (`id`, `instant`, `cliente_id`, `endereco_de_entrega_id`) VALUES
(1, '2019-11-01 15:43:00', 1, 1),
(2, '2019-11-07 11:27:00', 1, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `produto`
--

CREATE TABLE `produto` (
  `id` int(11) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `preco` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Despejando dados para a tabela `produto`
--

INSERT INTO `produto` (`id`, `nome`, `preco`) VALUES
(1, 'Computador', 2000),
(2, 'Impressora', 800),
(3, 'Mouse', 80),
(4, 'Mesa de escritório', 80),
(5, 'Toalha', 2000),
(6, 'Colcha', 800),
(7, 'TV true color', 80),
(8, 'Roçadeira', 80),
(9, 'Abajur', 2000),
(10, 'Pendente', 800),
(11, 'Shampoo', 80);

-- --------------------------------------------------------

--
-- Estrutura para tabela `produto_categoria`
--

CREATE TABLE `produto_categoria` (
  `produto_id` int(11) NOT NULL,
  `categoria_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Despejando dados para a tabela `produto_categoria`
--

INSERT INTO `produto_categoria` (`produto_id`, `categoria_id`) VALUES
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(4, 2),
(5, 3),
(6, 3),
(7, 4),
(8, 5),
(9, 6),
(10, 6),
(11, 7);

-- --------------------------------------------------------

--
-- Estrutura para tabela `telefone`
--

CREATE TABLE `telefone` (
  `cliente_id` int(11) NOT NULL,
  `telefones` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Despejando dados para a tabela `telefone`
--

INSERT INTO `telefone` (`cliente_id`, `telefones`) VALUES
(1, '34542874'),
(1, '9846753');

--
-- Índices de tabelas apagadas
--

--
-- Índices de tabela `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `cidade`
--
ALTER TABLE `cidade`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKkworrwk40xj58kevvh3evi500` (`estado_id`);

--
-- Índices de tabela `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `endereco`
--
ALTER TABLE `endereco`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8b1kcb3wucapb8dejshyn5fsx` (`cidade_id`),
  ADD KEY `FK8s7ivtl4foyhrfam9xqom73n9` (`cliente_id`);

--
-- Índices de tabela `estado`
--
ALTER TABLE `estado`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `item_pedido`
--
ALTER TABLE `item_pedido`
  ADD PRIMARY KEY (`pedido_id`,`produto_id`),
  ADD KEY `FKtk55mn6d6bvl5h0no5uagi3sf` (`produto_id`);

--
-- Índices de tabela `pagamento`
--
ALTER TABLE `pagamento`
  ADD PRIMARY KEY (`pedido_id`);

--
-- Índices de tabela `pagamento_com_boleto`
--
ALTER TABLE `pagamento_com_boleto`
  ADD PRIMARY KEY (`pedido_id`);

--
-- Índices de tabela `pagamento_com_cartao`
--
ALTER TABLE `pagamento_com_cartao`
  ADD PRIMARY KEY (`pedido_id`);

--
-- Índices de tabela `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK30s8j2ktpay6of18lbyqn3632` (`cliente_id`),
  ADD KEY `FK1fihyy2fnocpuwc74674qmfkv` (`endereco_de_entrega_id`);

--
-- Índices de tabela `produto`
--
ALTER TABLE `produto`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `produto_categoria`
--
ALTER TABLE `produto_categoria`
  ADD KEY `FKq3g33tp7xk2juh53fbw6y4y57` (`categoria_id`),
  ADD KEY `FK1c0y58d3n6x3m6euv2j3h64vt` (`produto_id`);

--
-- Índices de tabela `telefone`
--
ALTER TABLE `telefone`
  ADD KEY `FK8aafha0njkoyoe3kvrwsy3g8u` (`cliente_id`);

--
-- AUTO_INCREMENT de tabelas apagadas
--

--
-- AUTO_INCREMENT de tabela `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de tabela `cidade`
--
ALTER TABLE `cidade`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `endereco`
--
ALTER TABLE `endereco`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `estado`
--
ALTER TABLE `estado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `pedido`
--
ALTER TABLE `pedido`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `produto`
--
ALTER TABLE `produto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Restrições para dumps de tabelas
--

--
-- Restrições para tabelas `cidade`
--
ALTER TABLE `cidade`
  ADD CONSTRAINT `FKkworrwk40xj58kevvh3evi500` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`);

--
-- Restrições para tabelas `endereco`
--
ALTER TABLE `endereco`
  ADD CONSTRAINT `FK8b1kcb3wucapb8dejshyn5fsx` FOREIGN KEY (`cidade_id`) REFERENCES `cidade` (`id`),
  ADD CONSTRAINT `FK8s7ivtl4foyhrfam9xqom73n9` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`);

--
-- Restrições para tabelas `item_pedido`
--
ALTER TABLE `item_pedido`
  ADD CONSTRAINT `FK60ym08cfoysa17wrn1swyiuda` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`),
  ADD CONSTRAINT `FKtk55mn6d6bvl5h0no5uagi3sf` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`);

--
-- Restrições para tabelas `pagamento`
--
ALTER TABLE `pagamento`
  ADD CONSTRAINT `FKthad9tkw4188hb3qo1lm5ueb0` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`);

--
-- Restrições para tabelas `pagamento_com_boleto`
--
ALTER TABLE `pagamento_com_boleto`
  ADD CONSTRAINT `FKcr74vrxf8nfph0knq2bho8doo` FOREIGN KEY (`pedido_id`) REFERENCES `pagamento` (`pedido_id`);

--
-- Restrições para tabelas `pagamento_com_cartao`
--
ALTER TABLE `pagamento_com_cartao`
  ADD CONSTRAINT `FKta3cdnuuxclwfh52t4qi432ow` FOREIGN KEY (`pedido_id`) REFERENCES `pagamento` (`pedido_id`);

--
-- Restrições para tabelas `pedido`
--
ALTER TABLE `pedido`
  ADD CONSTRAINT `FK1fihyy2fnocpuwc74674qmfkv` FOREIGN KEY (`endereco_de_entrega_id`) REFERENCES `endereco` (`id`),
  ADD CONSTRAINT `FK30s8j2ktpay6of18lbyqn3632` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`);

--
-- Restrições para tabelas `produto_categoria`
--
ALTER TABLE `produto_categoria`
  ADD CONSTRAINT `FK1c0y58d3n6x3m6euv2j3h64vt` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`),
  ADD CONSTRAINT `FKq3g33tp7xk2juh53fbw6y4y57` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`);

--
-- Restrições para tabelas `telefone`
--
ALTER TABLE `telefone`
  ADD CONSTRAINT `FK8aafha0njkoyoe3kvrwsy3g8u` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
